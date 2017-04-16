app.factory('RecommendationService', [
    '$http', '$rootScope',
    function ($http, $rootScope) {
        var service = {};

        service.getRecommendation = function (songName, callback) {
            return $http.get('recommendation/' + songName, {});
        };
        return service;
    }]);
