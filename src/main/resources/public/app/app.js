/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var app = angular.module("App", ['ngRoute']);

app.config(['$routeProvider', '$httpProvider',
    function ($routeProvider, $httpProvider) {
        $routeProvider.when('/main', {
            templateUrl: 'app/partials/main.html',
            controller: 'MainController'
        }).when('/edit/:event_id', {
            templateUrl: 'app/partials/edit.html',
            controller: 'EditController'
        }).when('/about', {
            templateUrl: 'app/partials/about.html'
        }).otherwise({
            redirectTo: '/main'
        });

    }]);

app.run(['$rootScope', 'EventService', function ($rootScope, EventService) {
    $rootScope.errorMessage = "";
    $rootScope.webApiPath = '/api/';


}]);