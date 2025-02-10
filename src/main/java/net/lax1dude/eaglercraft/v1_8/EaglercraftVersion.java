package net.lax1dude.eaglercraft.v1_8;

import java.math.BigInteger;
import java.util.List;

public class EaglercraftVersion {

	//////////////////////////////////////////////////////////////////////

	/// Customize these to fit your fork:

	public static final String projectForkName = "Eaglercraft Lambda";
	public static final String projectForkVersion = "1.1.0 dev";
	public static final String projectForkVendor = "HoosierTransfer";

	public static final boolean isDevBuild = true;

	public static final String projectForkURL = "https://gitlab.com/lax1dude/eaglercraftx-1.8";

	//////////////////////////////////////////////////////////////////////

	public static final String projectOriginName = "EaglercraftX";
	public static final String projectOriginAuthor = "lax1dude";
	public static final String projectOriginRevision = "1.9";
	public static final String projectOriginVersion = "u39";
	
	public static final String projectOriginURL = "https://gitlab.com/lax1dude/eaglercraftx-1.8"; // rest in peace
	
	// EPK Version Identifier
	
	public static final String EPKVersionIdentifier = "u39"; // Set to null to disable EPK version check
	
	// Updating configuration

	public static final boolean enableUpdateService = true;

	public static final List<String> updateURLs = List.of(
			"https://hoosiertransfer.net/eaglerLcert",
			"https://temuzx.xyz/eaglerLcert",
			"https://eagler.xyz/eaglerLcert",
			"https://hoosiertransfer.xyz/eaglerLcert");

	public static final String updateBundlePackageName = "net.lax1dude.eaglercraft.v1_8.client";
	public static final int updateBundlePackageVersionInt = 3;

	public static final String updateLatestLocalStorageKey = "latestUpdate_" + updateBundlePackageName;

	// public key modulus for official 1.8 updates
	public static final BigInteger updateSignatureModulus = new BigInteger(
			"9934844152704206425984038360710846195785255499658630347555679233517037320419089417353684680137701223265944443284321705629368787894053054988233896240570752560271396448764359123258518818693879688207544671033079915029195517675413202427147319375331350380376604266826560830299822638274516845927247015696509586216934495843289602444650044805651410710164106192952455213102521880119736500301420208590760465989706511018182601545217390196438291842825959549203290633490664834390313090964927686415922400638755956780717898579080985306487440294133874610155281675147758926351882699414541707391045631732309999601681661304360813629331");

	// Client brand identification system configuration
	
	public static final EaglercraftUUID clientBrandUUID = EagUtils.makeClientBrandUUID(projectForkName);

	public static final EaglercraftUUID legacyClientUUIDInSharedWorld = EagUtils.makeClientBrandUUIDLegacy(projectOriginName);

	// Miscellaneous variables:

	public static final String mainMenuStringA = "Minecraft 1.9.4";
	public static final String mainMenuStringB = projectOriginName + " " +
			projectOriginRevision + "-" + projectOriginVersion + " ultimate";
	public static final String mainMenuStringC = "";
	public static final String mainMenuStringD = "Resources Copyright Mojang AB";

	public static final String mainMenuStringE = projectForkName + " " + projectForkVersion;
	public static final String mainMenuStringF = "Made by " + projectForkVendor;

	public static final String mainMenuStringG = "Collector's Edition";
	public static final String mainMenuStringH = "PBR Shaders";

	public static final long demoWorldSeed = (long) "North Carolina".hashCode();

	public static final boolean mainMenuEnableGithubButton = false;

	public static final boolean forceDemoMode = false;

	public static final String localStorageNamespace = "_eaglercraftX";
}
