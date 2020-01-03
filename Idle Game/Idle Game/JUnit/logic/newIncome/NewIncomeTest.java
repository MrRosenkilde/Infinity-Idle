package logic.newIncome;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import domain.Score;
import domain.State;
import logic.Logic;

@DisplayName("New Income Test")
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class NewIncomeTest {
	//integration test of the newIncome method in the Logic class
	//Has dependencies on state and state.statistics
	@Test
	public void Should_Add_Income_To_StatisticsTotalScore(@Mock Score score) {
		when(score.val()).thenReturn(BigDecimal.valueOf(10));
		State state = new State();
		Logic logic = new Logic(state);
		BigDecimal preScore = state.statistics().totalScore().val();
		logic.newIncome(score);
		BigDecimal postScore = state.statistics().totalScore().val();
		BigDecimal expected = preScore.add(score.val());
		assertEquals(expected,postScore);
	}
	@Test
	public void Should_Add_Income_To_StatisticsTotalScoreThisReset(@Mock Score score) {
		when(score.val()).thenReturn(BigDecimal.valueOf(10));
		State state = new State();
		Logic logic = new Logic(state);
		BigDecimal preScore = state.statistics().totalScoreThisReset().val();
		logic.newIncome(score);
		BigDecimal postScore = state.statistics().totalScoreThisReset().val();
		BigDecimal expected = preScore.add(score.val());
		assertEquals(expected,postScore);
	}
	@Test
	public void Should_Add_Income_To_Score(@Mock Score score) {
		when(score.val()).thenReturn(BigDecimal.valueOf(10));
		State state = new State();
		Logic logic = new Logic(state);
		BigDecimal preScore = state.score().val();
		logic.newIncome(score);
		BigDecimal postScore = state.score().val();
		BigDecimal expected = preScore.add(score.val());
		assertEquals(expected,postScore);
	}

}
