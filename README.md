# Kétszer ad, aki gyorsan ad

> Kétszer ad, aki gyorsan ad - Progressive Delivery Workshop

Az előadáshoz tartozó diasor elérhető itt: [diasor](./ketszer-ad-aki-gyorsan-ad.pdf).

## DishWish: A példaprogram

> **Figyelem**: Az alkalmazás semmiképpen sem alkalmas production környezetben történő üzemelésre. Célja csupán
a progressive delivery szemléltetése.

A példa egy ételrendelő alkalmazást valósít meg kicsiben. Vannak felhasználók, fogások, kategóriák és rendelések.

Természetesen szó sincs a teljes funkcionalitásról: csupán rendelést leadni és ajánlást kérni lehetséges. Utóbbi azt jelenti, hogy az alkalmazás ajánl egy fogást a felhasználó számára, nyilván azzal a céllal, hogy azt rendelje legközelebb.

Az alkalmazás célja, hogy bemutassa a progressive delivery két technikáját (shadowing, feature flagging) egy új ajánlási implementáción keresztül.

### Az alkalmazás futtatása

Az alkalmazás futtatásának nincsen semmilyen előkövetelménye.

Csupán adjuk ki a következő parancsot a repository gyökerében:

- Windows: 
  ```
  ./mvnw.cmd quarkus:dev
  ```
- *nix/MacOS: 
  ```
  ./mvnw quarkus:dev
  ```

Az alkalmazás a `8080`-as porton várja majd a HTTP lekérdezéseket, az adatokat pedig egy in-memory adatbázisban
fogja tárolni.

### Végpontok

Az elérhető végpontokat megtekinthetjük itt (előfeltétel, hogy fusson az alkalmazás):

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

Feature flagging megoldások:
- https://www.getunleash.io/
- https://flagsmith.com/
- https://launchdarkly.com
- https://posthog.com/
- https://configcat.com/
- https://www.growthbook.io/

Szemezgetés kapcsolódó forrásokból:
- https://increment.com/testing/i-test-in-production/
- https://copyconstruct.medium.com/testing-in-production-the-safe-way-18ca102d0ef1
- https://launchdarkly.com/blog/what-is-progressive-delivery-all-about/
- https://www.split.io/glossary/progressive-delivery/
- https://www.kameleoon.com/en/blog/progressive-delivery-concepts
- https://cloud.google.com/blog/products/gcp/cre-life-lessons-what-is-a-dark-launch-and-what-does-it-do-for-me/
- https://blog.turbinelabs.io/every-release-is-a-production-test-b31d80f2bc74
- https://blog.turbinelabs.io/deploy-not-equal-release-part-one-4724bc1e726b
- https://blog.turbinelabs.io/deploy-not-equal-release-part-two-acbfe402a91c
- https://increment.com/testing/launching-duolingos-arabic-language-course/
