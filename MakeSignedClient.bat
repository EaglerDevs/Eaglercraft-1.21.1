@echo off
title MakeSignedClient
java -cp "resources/MakeOfflineDownload.jar;resources/CompileEPK.jar" net.eaglerdevs.eaglercraft.v1_9_4.buildtools.workspace.MakeSignedClient "javascript/SignedBundleTemplate.txt" "javascript/classes.js" "javascript/assets.epk" "javascript/lang" "javascript/SignedClientTemplate.txt" "javascript/UpdateDownloadSources.txt" "javascript/EaglercraftL_1.9_Offline_Signed_Client.html"
pause