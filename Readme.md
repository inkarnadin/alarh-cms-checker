# Description
App for check information about web resources.
In this time supports next:
* CMS:
  * Joomla
  * WordPress
  * DataLife Engine
  * MaxSite CMS
  * Drupal
  * 1C-Bitrix
  * ModX
  * VamShop
  * Magento
  * OpenCart (ocStore)
  * Moguta.CMS
  * Shopify
  * HostCMS
  * UMI.CMS
* Web Frameworks:
  * Lavarel
  * Yii Framework
  * Nuxt.JS
  * Vue.JS
  * ReactJS
  * Ruby on Rails
* Site constructors:
  * Tilda
  * InSales
  * Vigbo
  * uKit    
  
Application can check using CMS and - in some case - checking CMS version. Also it checking about some additional 
modules such as PHP, PHPMyAdmin, web server and WHOIS info.

## Base Modifiers
* `-mm`  - disable main CMS checking module (optional);
* `-em`  - disable extended info checking module (optional);
* `-li`  - disable low importance results filter (optional). Returns the results of each find;
* `-who` - enable WhoIs info checking;
* `-th`  - enable extended info about theme;

## Example
`Set target host [example.com]: localhost -em -li`