package fpl.json

import com.devopsbuddy.fpl.json.LeagueData
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class LeagueDataTest {

    @Test
    fun testGetPlayersId() {
        val expectedIds = ArrayList<String>()
        expectedIds.add("232683")
        expectedIds.add("133944")
        expectedIds.add("153680")
        expectedIds.add("232612")
        expectedIds.add("232874")
        expectedIds.add("235052")
        expectedIds.add("235054")
        expectedIds.add("117937")
        val leagueData = LeagueData("31657")
        val playerIds = leagueData.playerIds
        expectedIds.sort()
        playerIds.sort()
        assertEquals(expectedIds, playerIds)
    }

}