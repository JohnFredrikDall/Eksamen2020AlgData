# Mappeeksamen i Algoritmer og Datastrukturer Høst 2020

# Krav til innlevering

Se oblig-tekst for alle krav, og husk spesielt på følgende:

* Git er brukt til å dokumentere arbeid (minst 2 commits per oppgave, beskrivende commit-meldinger)	
* git bundle er levert inn
* Hovedklassen ligger i denne path'en i git: src/no/oslomet/cs/algdat/Eksamen/EksamenSBinTre.java
* Ingen debug-utskrifter
* Alle testene i test-programmet kjører og gir null feil (også spesialtilfeller)
* Readme-filen her er fyllt ut som beskrevet


# Beskrivelse av oppgaveløsning (4-8 linjer/setninger per oppgave)

Vi har brukt git til å dokumentere arbeidet vårt. Jeg har 16 commits totalt, og hver logg-melding beskriver det jeg har gjort av endringer.

Jeg startet med å lagre den opprinnelige utleverte kildekoden

* Oppgave 1: Løste ved å ta utgangspunkt i oppgave 5.2 3a) fra kompendiet. Videre løste jeg oppgaven ved å se på 
             konstruktøren til Node<>() og så at den ønsket en peker til forelder. Node q pekte allerde på forelder, 
             så det var bare å sette inn q som forelder i konstruktøren.
* Oppgave 2: Løste ved å bruke en algoritme fra ukesoppgavene med en liten endring. Endringen var å kjøre en tom() 
             sjekk før jeg begynte å allokere pekere. Videre traverseres hele treet og antallVerdier øker for hver node
             riktig verdi blir besøkt.
* Oppgave 3: Denne oppgaven løste jeg ved hjelp av rekursjon. Jeg brukte ganske lang tid på den for å få det riktig, 
             fordi jeg ikke lagret verdien av hvert rekursive kall til en variabel. Etter mye debugging greide jeg å 
             finne problemet og korrigere det. nestePostorden(Node<T> p) løste jeg ved å bruke kommentarer fra 
             kompendiet som beskrev de ulike situasjonene. For så å kode inn akkurat det kommentaren beskrev, funket 
             som det skulle.
* Oppgave 4: Løste første del av oppgaven, dvs postorden(Node<T> p, Oppgave <? super T> oppgave) ved å bruke en liknende whileloop
             brukt i toStringPostorden metoden. Videre ble den rekursive varianten ganskek simpel, siden jeg løste oppgave
             3 rekursivt. Så jeg kopierte koden fra den oppgaven og skrev det om til å passe inn her. For så å kalle metoden
             med rotnode som input i postorden(Oppgave <? super T> oppgave). Tester funker som de skal!
* Oppgave 5: Her løste jeg først serialiseringen ved å opprette en ArrayList<T> for å lagre verdiene, for så å opprette
             ArrayDeque<Node<T>>. Ved å bruke et FIFO kommer listen ut med riktig resultat. Deserialisering igjen løste 
             jeg ved å først sette første element i listen til rotnode. For så å traversere arrayet med en for-løkke og bruke
             leggInn metoden til å legge inn hvert element i riktig orden. Tester fungerer fint!
* Oppgave 6: Løste dette ved å kopiere koden fra Programkode 5.2 8 d). For å få til å teste at alle pekerne var riktig
             tegnet jeg opp binærtreet fra test-koden på ark. Og prøvde koden på alle mulige ulike måter. Brukte en del
             tid på å finne hvor disse forelder-pekerne skulle feile. Trodde først de kom til å skje i den mest kompliserte
             delen av koden (case 3). Så brukte mye tid på å forstå hvor feilen skulle komme, så testet jeg de enkleste 
             casene og fant ut at det var her pekerne ble feil. Justerte for dette og kodet resten av metodene. fjernAlle
             løste jeg med en enkel while-løkke. nullstill() kopierte jeg fra 5.1.18 12, for å løse oppgaven slik måtte
             jeg lage en hjelpemetode som kunne kalles rekursivt. Håper det er ok! Tester fungerer som de skal.