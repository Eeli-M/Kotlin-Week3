MVVM on arkkitehtuurimalli, joka muodostuu kolmesta osasta.
  1. Model, joka edustaa dataa ja toimintalogiikkaa. Esim. Task-dataluokka.
  2. View, sovelluksen UI. Sillä näytetään käyttäjälle tietoa ja se käsittelee käyttäjän syötteitä, kuten klikkauksia.
  3. ViewModel, joka toimii Modelin ja Viewin välillä. Se esimerkiksi käsittelee käyttäjän toimintoja, kuten tehtävän lisäys tai poisto.
MVVM:n avulla esim. UI voidaan pitää stateless tilassa, koska tila tulee ViewModelista. Sovellusta on myös helpompi testata ja ylläpitää, kun toimintalogiikka on erillään UI:sta.

StateFlow:lla voi pitää tilaa yllä ja havainnoida sen muutoksia. Nykyisen arvon voi saada .value-toiminnolla ja sen voi kerätä collectAsState() funktiolla, sekä päivittää automaattisesti UI:n, kun arvo muuttuu.
