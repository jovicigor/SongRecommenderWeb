app.controller('EditController', ['$scope', '$location', '$routeParams', 'EventService', function ($scope, $location, $routeParams, EventService) {

    console.log($routeParams.event_id);

    EventService.getEventById($routeParams.event_id, function (response) {
        $scope.event = response;
    });

    $scope.updateEvent = function () {
        EventService.updateEvent($scope.event.id, $scope.event, function (response) {
            $location.path('/main');
        });
    };

    $scope.deleteEvent = function () {
      if(confirm("Are you sure?")){
        EventService.deleteEvent($scope.event.id, function (response) {
            $location.path('/main');
        });
      }
    };
}]);