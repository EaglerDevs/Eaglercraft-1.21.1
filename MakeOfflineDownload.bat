@echo off
title MakeOfflineDownload
java -cp "resources/MakeOfflineDownload.jar;resources/CompileEPK.jar" net.eaglerdevs.eaglercraft.v1_9_4.buildtools.workspace.MakeOfflineDownload "javascript/OfflineDownloadTemplate.txt" "javascript/classes.js" "javascript/assets.epk" "javascript/EaglercraftL_1.9_Offline_en_US.html" "javascript/EaglercraftL_1.9_Offline_International.html" "javascript/lang"
pause