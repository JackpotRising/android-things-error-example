Temporary Crashlytics setup:

https://www.fabric.io/datecart/android/apps/com.jackpotrising.jackpotalley/

(Will switch to Jackpot Rising keys at a later date)

See network traffic via Stetho by navigating to chrome://inspect in Chrome

adb connect 192.168.1.109

am startservice -n com.google.wifisetup/.WifiSetupService -a WifiSetupService.Connect -e ssid “neoseoul102” -e passphrase “ns12345678”

adb reboot