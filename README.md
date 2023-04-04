# Kétszer ad, aki gyorsan ad

> Kétszer ad, aki gyorsan ad - Progressive Delivery Workshop

Az előadáshoz tartozó diasor elérhető itt: [diasor](./ketszer-ad-aki-gyorsan-ad.pdf).

## DishWish: A példaprogram

A példa egy ételrendelő alkalmazást valósít meg kicsiben. Vannak felhasználók, fogások, kategóriák és rendelések.

Természetesen szó sincs a teljes funkcionalitásról: csupán rendelést leadni és ajánlást kérni lehetséges.

Az utóbbi funkcionalitás, az ajánlás lesz az, amire alternatív implementációt fogunk készíteni.

*Figyelem*: Az alkalmazás semmiképpen sem alkalmas production környezetben történő üzemelésre. Célja csupán
a progressive delivery szemléltetése.

### Az alkalmazás futtatása

Az alkalmazás futtatásának nincsen semmilyen előkövetelménye.

Csupán adjuk ki a következő parancsot a repository gyökerében:

- Windows: `./mvnw.cmd quarkus:dev`
- *nix/MacOS: `./mvnw quarkus:dev`

Az alkalmazás a `8080`-as porton várja majd a HTTP lekérdezéseket, az adatokat pedig egy in-memory adatbázisban
fogja tárolni.

### Végpontok

Az elérhető végpontokat megtekinthetjük itt:

- http://localhost:8080/q/swagger-ui/

A lekérdezésekhez használhatjuk a fenti felületet, avagy például Postmant.

### Autentikáció

Az alkalmazás végpontjai HTTP Basic autentikációs sémával védettek. Két felhasználó elérhető:

- `bob` / `password`
- `admin` / `admin`

## Mérföldkövek

Commitról commitra, az alábbi mérföldkövekre bonthatjuk az ajánlási rendszer fejlesztését:

- [Alaphelyzet](https://github.com/battila7/ketszer-ad-aki-gyorsan-ad/tree/77444dd7ce8f2ea5e4b323c950c973bfa4c1e812) 
  - Az ajánló végpontot meghívva, mindig a kedvenc ételünket kapjuk, mint ajánlás.
- [Shadowing](https://github.com/battila7/ketszer-ad-aki-gyorsan-ad/tree/71b80cf728a09f5c819b29071493fc0a628e28b2)
  - Ha meghívjuk az ajánló végpontot, akkor továbbra is mindig a kedvenc ételünket fogjuk kapni. Azonban, a shadowing
  technika keretében meghívunk mindig egy alternatív implementációt is: mely a kedvenc ételünkhöz hasonló ételt próbál
  választani. Ezt az ajánlást logoljuk, majd eldobjuk.
- [Feature Flagging](https://github.com/battila7/ketszer-ad-aki-gyorsan-ad/tree/792d404f8a4fa666cc0740ff35d03fe3abadf174)
  - Bevezetünk egy `similarity-based-dish-recommendation` nevű feature flaget, kezdetben 0 valószínűséggel. Ezt a
  valószínűséget a `POST /feature-flag/similarity-based-dish-recommendation/adjust` végpontot hívva változtathatjuk. Ha
  a flag bekapcsoltra értékelődik ki, akkor az ajánló végpont az új implementáció kimenetét adja vissza, egyébként pedig
  a régiét. 

## Ajánlott irodalom

Szemezgetés kapcsolódó forrásokból:

- https://increment.com/testing/i-test-in-production/
- https://launchdarkly.com/blog/what-is-progressive-delivery-all-about/
- https://www.split.io/glossary/progressive-delivery/
- https://www.kameleoon.com/en/blog/progressive-delivery-concepts
- https://cloud.google.com/blog/products/gcp/cre-life-lessons-what-is-a-dark-launch-and-what-does-it-do-for-me/
- https://blog.turbinelabs.io/every-release-is-a-production-test-b31d80f2bc74
- https://blog.turbinelabs.io/deploy-not-equal-release-part-one-4724bc1e726b
- https://blog.turbinelabs.io/deploy-not-equal-release-part-two-acbfe402a91c
- https://copyconstruct.medium.com/testing-in-production-the-safe-way-18ca102d0ef1
- https://increment.com/testing/launching-duolingos-arabic-language-course/
