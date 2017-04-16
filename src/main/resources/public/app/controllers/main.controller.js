app.controller('MainController', ['$scope', '$rootScope', 'RecommendationService', function ($scope, $rootScope, RecommendationService) {

    var playing = false;
    $scope.recommendationAudio = undefined;
    $scope.requestAudio = undefined;


    $scope.getRecommendation = function () {
        RecommendationService.getRecommendation($scope.songName)
            .then(function (response) {
                console.log(response);
                $scope.recommendation = response.data.recommendation;
                $scope.recommendationAudio = new Audio($scope.recommendation.previewUrl);

                $scope.request = response.data.request;
                $scope.requestAudio = new Audio($scope.request.previewUrl);
                $scope.showPlayMenu = true;
            });
    };

    $scope.playRecommendation = function () {
        $scope.recommendationAudio.play();
    };

    $scope.pauseRecommendation = function () {
        if ($scope.recommendationAudio)
            $scope.recommendationAudio.pause();
    };
    $scope.playRequest = function () {
        $scope.requestAudio.play();
    };

    $scope.pauseRequest = function () {
        if ($scope.requestAudio)
            $scope.requestAudio.pause();
    }
}]);
