var app = angular.module("myApp", ["ngRoute"]);
    app.config(function($routeProvider) {
        $routeProvider
        .when("/", {
            templateUrl : "search.html",
            controller : "searchCtrl"
        }).when("/result", {
            templateUrl : "result.html",
            controller : "resultCtrl"
        }).when("/reserve", {
            templateUrl : "res.html",
            controller : "resultCtrl"
        });
    });

    app.controller("searchCtrl", function ($scope, $rootScope, $http, $location) {
        $rootScope.pageName = "search";
        $scope.searchFlights = function () {
        var body = {
            src : $scope.source,
            dest : $scope.dest,
            startDate : $scope.date,
            endDate : $scope.date2,
            numOfAdults : $scope.adults,
            numOfChildren : $scope.children,
            numOfInfants: $scope.infants
        };
            console.log(body);

            $http.post('http://localhost:8080/ali/booking/searchAll', body)
            .then(function(response) {
                  $rootScope.flights = response.data.flights;
                  $rootScope.numOfFlights = response.data.numOfFlights;
                  console.log($rootScope.flights);
                  $location.url('/result');
              });
        };
    });

    app.controller("resultCtrl", function ($scope, $rootScope, $http, $location) {
        $rootScope.pageName = "result";
    });