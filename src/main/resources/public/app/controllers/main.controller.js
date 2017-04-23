app.controller('MainController', ['$scope', '$rootScope', 'RecommendationService', function ($scope, $rootScope, RecommendationService) {

    $scope.loading = false;
    $scope.loaded = false;
    $scope.error = false;
    $scope.recommendationAudio = undefined;
    $scope.requestAudio = undefined;


    $scope.getRecommendation = function () {
        $scope.loaded = false;
        $scope.loading = true;
        $scope.error = false;
        RecommendationService.getRecommendation($scope.songName)
            .then(function (response) {
                    $scope.recommendation = response.data.recommendation[0];
                    $scope.recommendationAudio = new Audio(response.data.recommendation[0].previewUrl);

                    $scope.request = response.data.request;
                    $scope.requestAudio = new Audio($scope.request.previewUrl);

                    $scope.loading = false;
                    $scope.loaded = true;
                },
                function (data) {
                    $scope.error = true;
                });

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
    };
}]);
