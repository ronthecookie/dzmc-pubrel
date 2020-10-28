# MC Dangerzone plugin (1.15.2) Public Release
Not working on that server anymore so might as well release the plugin's code for everyone to see and use if they wish.

I hope by releasing this someone goes out there and makes a better SCP MC server with this so Dangerzone gets some real competition.

## Usage
You can download it [here](https://github.com/ronthecookie/dzmc-pubrel/releases) (or [here](https://github.com/ronthecookie/dzmc-pubrel/releases/download/v2.0/dzmc-2-1.15.2.jar) if you're extra lazy) and edit the configuration in `plugins/Dangerzone/config.yml`.

Run with paper for best performance.

### Dependencies
You need:
- [EssentialsX](https://essentialsx.cf/)
- [Vault](https://www.spigotmc.org/resources/vault.34315/)
- [LuckPerms](https://luckperms.net/)

### Permissions
- `dangerzone.friendly.scpf`
- `dangerzone.friendly.ci`
- `dangerzone.infinijump`
- `dangerzone.doublejump`
- `dangerzone.cuffs`
- `dangerzone.uncuffable`
- `dangerzone.mgmc` (machine gun minecart)
- `dangerzone.punish`
- `dangerzone.staffchat`
- `dangerzone.scp914no`
- `dangerzone.markcard`
- `dangerzone.suspend`
- `dangerzone.unsuspendable`
#### Radios
- `dangerzone.radios.RADIO`
- `dangerzone.radios.CHAOS`
- `dangerzone.radios.IA`
- `dangerzone.radios.ISD`
- `dangerzone.radios.MTF`
- `dangerzone.radios.SCD`
- `dangerzone.radios.SD`
- `dangerzone.radios.SRU`
- `dangerzone.radios.STAFF`
### Can I has 1.16??
Maybe... Just try it and see what happends, I haven't tested it.

## Features

- Money blocks (click to get money)
- Respawn kits (with [CookieKits](https://github.com/ronthecookie/cookiekits) but it should work with other plugins too)
- ISD Cuffs (drag a player with you)
- Medkit (heals the player you click it with)
- ~~Keycards (L1-L4, Omni)~~
- Machine gun minecarts with Crackshot
- Radios (for all departments too)
- SCP-1437 (read the code)
- Partially implemented SCP-914 (some of it removed because no keycards included.)
- /punish <player>
- Double jump
- Cafeteria Mission
- CI logic that integrates with LuckPerms (set back to default on death, etc - read the code)
- /casino (yeah I don't like it either)
- /suspend (with LuckPerms)

More? Open an issue if I forgot something.

### Keycard
~~Any item can be marked as a keycard with `/markcard <level>`. Certain redstone inputs (different buttons & the lever) are mapped to [certain keycard levels](https://github.com/ronthecookie/dzmc-pubrel/blob/master/src/main/java/me/ronthecookie/dzmc/keycards/Keycard.java).~~

## Support
None. I don't care about Dangerzone or this plugin anymore so expect bugs and shitty code.

## License
MIT, Check the `LICENSE` file for more details.