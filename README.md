# Проект по автоматизации тестирования API на сайте reqres.in
<p align="center">
<a href="https://reqres.in/"><img src="images/screen/logoReqres2.jpg" alt="reqres.in"/></a>
</p>

Документация для сайта reqres.in выложена на главной странице и доступна всем пользователям.
<p align="center">
<img src="images/screen/apiDocs2.PNG" alt="apiDocs"/>
</p>



## :memo: Содержание:
- [Реализованные проверки](#Реализованные-проверки)
- [Технологии](#boom-Технологии)
- [Сборка в Jenkins](#man_cook-Jenkins-job)
- [Allure отчет](#bar_chart-Allure-отчет)

## Реализованные проверки
1. Запрос GET список пользователей:
+ Проверка, ответ от сервера соответствует scheme
+ Проверка, что у каждого пользователя в строке Avatar есть id
+ Проверка, что Email оканчивается на reqres.in

2. Запрос POST:
+ Проверка, что id, token соответствуют заданным
+ Проверка, что текст ошибки соответствует заданному

3. Запрос DELETE проверка кода ответа от сервера
4. Запрос GET Проверка, что в ответе пользователи отсортированы по году
5. Запрос PUT проверка, что время которое возвращается в ответе, совпадает с серверным

## :boom: Технологии
<p align="center">
<img width="6%" title="Idea" src="images/logo/Idea.svg">
<img width="6%" title="Java" src="images/logo/Java.svg">
<img width="6%" title="Allure Report" src="images/logo/Allure.svg">
<img width="6%" title="Gradle" src="images/logo/Gradle.svg">
<img width="6%" title="JUnit5" src="images/logo/Junit5.svg">
<img width="6%" title="GitHub" src="images/logo/GitHub.svg">
<img width="6%" title="Jenkins" src="images/logo/Jenkins.svg">
<img width="6%" title="REST Assured" src="images/logo/logo-transparent.png">
</p>


- Автотесты написаны на **Java** с использованием  **RestAssured**.
- Для сборки проекта используется **Gradle**.
- **JUnit 5** используется как фреймворк для модульного тестирования.
- Запуск тестов выполняется из **Jenkins**.
- **Allure Report** для визуализации результатов тестирования.



## :man_cook: Jenkins job
<img src="images/logo/Jenkins.svg" width="25" height="25"  alt="Jenkins"/></a>  <a target="_blank" href="https://jenkins.autotests.cloud/job/011-LMaslo-api-diplom/">Jenkins job</a>
<p align="center">
<a href="https://jenkins.autotests.cloud/job/011-LMaslo-api-diplom/"><img src="images/screen/jenkins2.png" alt="Jenkins"/></a>
</p>


###  Локальный запуск:
```
gradle clean test
```

## :bar_chart: Allure-отчет
<img src="images/logo/Allure.svg" width="25" height="25"  alt="Allure"/></a> Отчет в <a target="_blank" href="https://jenkins.autotests.cloud/job/011-LMaslo-api-diplom/4/allure/">Allure report</a>
<p align="center">
<a href="https://jenkins.autotests.cloud/job/011-LMaslo-api-diplom/4/allure/"><img src="images/screen/allure.png" alt="Allure"/></a>
  <a href="https://jenkins.autotests.cloud/job/011-LMaslo-api-diplom/4/allure/"><img src="images/screen/allure2.png" alt="Allure"/></a>
</p>
