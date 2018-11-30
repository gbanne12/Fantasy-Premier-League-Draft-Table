import com.devopsbuddy.fpl.GameweekMonth
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
        data.getLeagueIdentifier() == "31657"
    }

    def "can get the player name from the player identifier"() {
        given:
        PlayerData data = new PlayerData("235052")

        expect:
        data.getName() == "Gary Bannerman"
    }

    def "can get the team name from the player identifier"() {
        given:
        PlayerData data = new PlayerData("235052")

        expect:
        data.getTeam() == "Three-peat"
    }

    def "can get the monthly score from the player identifier"() {
        given:
        PlayerData data = new PlayerData("235052")

        expect:
        data.getScore(GameweekMonth.SEPTEMBER) == 179
    }
}