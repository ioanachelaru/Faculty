#### Laborator 4 <br>
- Folosirea executiei concurente prin apeluri asincrone.
- Folosirea mecanismelor: future, promises si thread_pool.
- Analiza imbunatatirii performantei executiei unei aplicatii (de tip business) prin
programare concurenta.
Sala concerte
O sala de concerte vinde bilete la spectacolele organizate printr-o aplicatie client-server. Sala are trei
categorii de locuri cu preturi diferite. Pentru fiecare categorie exista o lista de locuri care pot fi vandute.
Pentru fiecare spectacol avem informatii de tip (ID_spectacol, data, titlu, descriere).
Se pot opera mai multe vanzari simultane ! <br><br>
Permanent se mentine o evidenta actualizata pentru:
- informatii despre bilete pentru fiecare spectacol - (data, ID_spectacol, lista_locuri_ vandute);
- vanzarile efectuate: lista de vanzari; vanzare = (data_vanzare, ID_spectacol, numar_bilete,
lista_locurilor) ;
- soldul total (suma totala incasata).
Periodic sistemul face o verificare a locurilor vandute prin verificarea corespondentei corecte intre
locurile libere si vanzarile facute (de la ultima verificare pana in prezent), sumele incasate in aceeasi
perioada si soldul total. <br><br>
Sistemul foloseste un mecanism de tip ‘Thread-Pool’ pentru rezolvarea concurenta a vanzarilor.
Pentru a testare se va folosi un thread care initiaza/creeaza la interval de 5 sec o noua cerere de vanzare
bilete folosind date generate aleatoriu.
Pentru verificare se cere salvarea pe suport extern a soldului, a listei vanzarilor si a rezultatelor
operatiilor de verificare executate periodic.

Limbajul de implementare: la alegere 