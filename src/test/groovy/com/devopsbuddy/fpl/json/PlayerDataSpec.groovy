import com.devopsbuddy.model.GameMonth
import com.devopsbuddy.fpl.json.PlayerData
import spock.lang.Specification

/**
 * Tests for the Player Data json responses
 * Tests based on my player id which is 235052.
 */

class PlayerDataSpec extends Specification {

    def "can get the league identifier from the player identifier"() {
        given:
        PlayerData data = new PlayerData("235052")

        expect:
        data.getLeagueIdentifier() != null
        data.getLeagueIdentifier() == "31657"
    }

    def "can get the player name from the player identifier"() {
        given:
        PlayerData data = new PlayerData("235052")

        expect:
        data.getName() != null
        data.getName() == "Gary Bannerman"
    }

    def "can get the team name from the player identifier"() {
        given:
        PlayerData data = new PlayerData("235052")

        expect:
        data.getTeam() != null
        data.getTeam() == "Three-peat"
    }

    def "can get the monthly score from the player identifier"() {
        given:
        PlayerData data = new PlayerData("235052")

        expect:
        data.getScore(GameMonth.SEPTEMBER) > 0
        data.getScore(GameMonth.SEPTEMBER) == 179
    }

    def "can get the monthly totals from the player identifier"() {
        expect:
        PlayerData data = new PlayerData("235052")
        data.getScore(month) == total

        where:
        month | total
        GameMonth.SEPTEMBER | 179
        GameMonth.AUGUST | 131
        GameMonth.MAY | 0


    }
}