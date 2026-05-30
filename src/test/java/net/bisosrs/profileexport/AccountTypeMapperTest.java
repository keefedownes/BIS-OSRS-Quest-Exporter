package net.bisosrs.profileexport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import net.bisosrs.profileexport.mapping.AccountTypeMapper;
import net.bisosrs.profileexport.mapping.DiaryTierThreshold;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Varbits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccountTypeMapperTest
{
	@Mock
	private Client client;

	@Test
	public void mapsIronmanAccountType()
	{
		when(client.getGameState()).thenReturn(GameState.LOGGED_IN);
		when(client.getVarbitValue(Varbits.ACCOUNT_TYPE)).thenReturn(1);
		assertEquals("ironman", AccountTypeMapper.map(client));
		assertEquals("Ironman", AccountTypeMapper.displayLabel("ironman"));
	}

	@Test
	public void unknownWhenLoggedOut()
	{
		when(client.getGameState()).thenReturn(GameState.LOGIN_SCREEN);
		assertEquals("unknown", AccountTypeMapper.map(client));
	}
}
