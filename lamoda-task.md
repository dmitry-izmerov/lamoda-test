Необходимо написать программу клиент - серверной архитектуры, которая получает с клиента xml файл следующей структуры :
```
 <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
    <SOAP-ENV:Header xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"/>
    <soap:Body>
        <LoadingStockStateResponse xmlns="http://lamoda.ru/xsd/wms/stock-state">
            <item>
                <id>7<id/>
                <sku>ME146ECES289CH1460</sku>
                <count>1</count>
            </item>
            <item>
                <id>10<id/>
                <sku>WR224AMCX569E410</sku>
                <count>1</count>
            </item>
            <item>
                <id>12<id/>
                <sku>VI060AWEE738R390</sku>
                <count>2</count>
            </item>
        </LoadingStockStateResponse>
    </soap:Body>
 </soap:Envelope>
```
и записывает в поток вывода (другой файл на жестком диске) информацию в виде key:value

####Условия:

1) Нужно проверять входящий файл на корректность, xml должен быть well-formed & valid
2) sku не повторяются в сообщении
3) Входящий поток данных может превышать 100 GB
4) Серверное приложение должно работать в JEE окружении на Application Server

####Будет плюсом:
- Если в качетсве Application Server будет выбран Wildfly;
- Если клиент будет иметь WEB интерфейс
- Если WEB интерфейс клиента будет реализован с использованием AngularJS (v1 / v2) и Bootstrap (v3 / v4)
- Если общение будет реализовано через веб сервис
- Если вместо файла сохранение будет происходить в БД (предпочтительней PostgreSQL)

Также необходимо прислать инструкцию по сборке / настройке / запуску приложения.
Передачу кода можно оформить любым удобным способом (архив / гитхаб).