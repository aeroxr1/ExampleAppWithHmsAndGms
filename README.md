# pocHmsGms
Questo è un piccolo poc con tre moduli:
- App
- Core
- Platform

Ho inserito tre flavor di esempio : alpha, beta e prod con flavorDimension "bundleId" e due flavor google e huawei con flavorDimension "platform".

Nel modulo app, vi è l'activity principale del poc da cui si può accedere alle varie schermate di test contenute nel modulo core.

Nel modulo platform vi sono le implementazioni hms e gms dei servizi di push, mappe e qrCode.

Ora per scopo di test solamente le push sono implementate sia in formato hms che gms, mentre qrCode e mappe sono implementate nel modulo core solamente in formato hms.