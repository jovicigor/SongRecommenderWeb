app.controller('MainController', ['$scope', '$rootScope', 'EventService', function ($scope, $rootScope, EventService) {

    EventService.getAllEvents(function (response) {
        $scope.events = response;
    });

    $scope.createEvent = function () {
        EventService.createEvent($scope.event, function (response) {
            $scope.events.push(response);
            $scope.event = {}
        });

    }
}]);
