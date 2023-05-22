package lu.combopt.tournament.score;

import lu.combopt.tournament.domain.Game;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.impl.heuristic.selector.move.generic.chained.SubChainChangeMove;

import java.time.temporal.ChronoUnit;
import java.time.DayOfWeek;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.optaplanner.core.api.score.stream.Joiners.equal;

public class TournamentSchedulerConstraintProvider implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {

        return new Constraint[]{
                manyGamesForDateTimeSlot(constraintFactory),
                sameStadium(constraintFactory),
                //gameStartsBefore10(constraintFactory),
                sameTeam(constraintFactory),
                gameNotAtWeekend(constraintFactory),
                daysBetweenGames(constraintFactory),
                gameWorkBefore18(constraintFactory),
                //gameWorkAfter21(constraintFactory),
                //gameWeekendAfter21(constraintFactory),
                gameWeekendBefore12(constraintFactory)
        };
    }

    //Hard - No more than 1 game for each team per day
    private Constraint sameTeam(ConstraintFactory constraintFactory){
        return  constraintFactory
                .forEachUniquePair(Game.class, equal(Game::getGameDate))
                .filter((g1, g2) -> g1.getHomeTeam() == g2.getHomeTeam() || g1.getAwayTeam() == g2.getAwayTeam()
                ||g1.getHomeTeam() == g2.getAwayTeam() || g1.getAwayTeam() == g2.getHomeTeam())
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("moreThanOneGameTeam");
    }


    //Hard - Game starts only between 10.00 and 23.00
    private Constraint gameStartsBefore10(ConstraintFactory constraintFactory){
        return  constraintFactory
                .forEach(Game.class)
                .filter(g -> g.getDateTimeSlot().getDateTimeStart().getHour()<10)
                .penalize(HardSoftScore.ONE_HARD, g -> 10 - g.getDateTimeSlot().getDateTimeStart().getHour())
                .asConstraint("gameStartsBefore10");
    }

    //Hard - Only 1 game for stadium per day
    private Constraint sameStadium(ConstraintFactory constraintFactory){
        return  constraintFactory
                .forEachUniquePair(Game.class, equal(Game::getStadium))
                .filter((g1, g2) -> g1.getGameDate() == g2.getGameDate())
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("moreThanOneGameStadium");
    }

    //Soft - No more than 1 team for date time slot
    private Constraint manyGamesForDateTimeSlot(ConstraintFactory constraintFactory){
        return  constraintFactory
                .forEachUniquePair(Game.class, equal(Game::getDateTimeSlot))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("gamesAtSameTime");
    }

    //Soft - Games should be scheduled on weekend and Friday
    private Constraint gameNotAtWeekend(ConstraintFactory constraintFactory){
        return  constraintFactory
                .forEach(Game.class)
                .filter(g -> !(List.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY))
                        .contains(g.getDateTimeSlot().getDateTimeStart().getDayOfWeek()))
                .penalize(HardSoftScore.ofSoft(3))
                .asConstraint("gameNotAtWeekend");
    }


    //Soft - 2+ days pause between games for each team
    private Constraint daysBetweenGames(ConstraintFactory constraintFactory){
        return  constraintFactory
                .forEachUniquePair(Game.class)
                .filter((g1, g2) -> (g1.getHomeTeam() == g2.getHomeTeam() || g1.getAwayTeam() == g2.getAwayTeam()
                        ||g1.getHomeTeam() == g2.getAwayTeam() || g1.getAwayTeam() == g2.getHomeTeam())
                        && Math.abs(DAYS.between(g1.getGameDate(), g2.getGameDate())) < 3)
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("freeDaysBetweenGames");
    }

    //Soft - games starts between 18.00 and 21.00 on work days
    private Constraint gameWorkBefore18(ConstraintFactory constraintFactory){
        return  constraintFactory
                .forEach(Game.class)
                .filter(g -> !List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(g.getDateTimeSlot().getDateTimeStart().getDayOfWeek())
                        && (g.getDateTimeSlot().getDateTimeStart().getHour() < 18))
                .penalize(HardSoftScore.ONE_SOFT, g -> (18 - g.getDateTimeSlot().getDateTimeStart().getHour()))
                .asConstraint("gameWorkBefore18");
    }
    private Constraint gameWorkAfter21(ConstraintFactory constraintFactory){
        return  constraintFactory
                .forEach(Game.class)
                .filter(g -> !List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(g.getDateTimeSlot().getDateTimeStart().getDayOfWeek())
                        && (g.getDateTimeSlot().getDateTimeStart().getHour() > 21))
                .penalize(HardSoftScore.ONE_SOFT, g -> (g.getDateTimeSlot().getDateTimeStart().getHour() - 20) / 2)
                .asConstraint("gameWorkAfter21");
    }

    //Soft - games starts between 12.00 and 21.00 on weekend
    private Constraint gameWeekendBefore12(ConstraintFactory constraintFactory){
        return  constraintFactory
                .forEach(Game.class)
                .filter(g -> List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(g.getDateTimeSlot().getDateTimeStart().getDayOfWeek())
                        && (g.getDateTimeSlot().getDateTimeStart().getHour() < 12))
                .penalize(HardSoftScore.ONE_SOFT, g -> (12 - g.getDateTimeSlot().getDateTimeStart().getHour()))
                .asConstraint("gameWeekendBefore12");
    }
    private Constraint gameWeekendAfter21(ConstraintFactory constraintFactory){
        return  constraintFactory
                .forEach(Game.class)
                .filter(g -> List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(g.getDateTimeSlot().getDateTimeStart().getDayOfWeek())
                        && (g.getDateTimeSlot().getDateTimeStart().getHour() > 21))
                .penalize(HardSoftScore.ONE_SOFT, g -> (g.getDateTimeSlot().getDateTimeStart().getHour() - 20) / 2)
                .asConstraint("gameWeekendAfter21");
    }
}
