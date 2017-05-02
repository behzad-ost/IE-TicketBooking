var app = angular.module("myApp", ["ngRoute"]);

app.filter('city', function() {
    return function(code) {
          if(code === "MHD") {
            return "مشهد";
          } else if(code === "THR") {
            return "تهران";
          }
      }
});


    app.config(function($routeProvider) {
        $routeProvider
        .when("/", {
            templateUrl : "search.html",
            controller : "searchCtrl"
        }).when("/result", {
            templateUrl : "result.html",
            controller : "resultCtrl"
        }).when("/reserve", {
            templateUrl : "reserve.html",
            controller : "reserveCtrl"
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
                  $rootScope.adults = $scope.adults;
                  $rootScope.children = $scope.children;
                  $rootScope.infants = $scope.infants;
                  $rootScope.numOfFlights = response.data.numOfFlights;
                  console.log($rootScope.flights);
                  $location.url('/result');
              });
        };
    });

    app.controller("resultCtrl", function ($scope, $rootScope, $http, $location) {
        $rootScope.pageName = "result";
        $scope.reserveFlight = function (index) {
            console.log(index);
            $rootScope.flight = $rootScope.flights[index];
            var body = {
                arrivalTime : $rootScope.flights[index].arrivalTime,
                departureTime: $rootScope.flights[index].departureTime,
                flightNumber : $rootScope.flights[index].flightNumber,
                airlineCode : $rootScope.flights[index].airlineCode,
                planeModel : $rootScope.flights[index].planeModel,
                dest :$rootScope.flights[index].dest,
                seatClassName :$rootScope.flights[index].seatClassName,
                origin : $rootScope.flights[index].origin,
                date : $rootScope.flights[index].date,
                numOfInfants : $rootScope.infants,
                numOfChildren : $rootScope.children,
                numOfAdults : $rootScope.adults
            }
            console.log(body);
            $http.post('http://localhost:8080/ali/booking/reserve', body)
            .then(function(response) {
                  $rootScope.adultSum = response.data.adultSum;
                  $rootScope.infantSum = response.data.infantSum;
                  $rootScope.childSum = response.data.childSum;
                  $rootScope.totalSum = response.data.totalSum;
                  $rootScope.adultFee = response.data.adultFee;
                  $rootScope.childFee= response.data.childFee;
                  $rootScope.infantFee= response.data.infantFee;

                  $location.url('/reserve');
              });
        }
    });

    app.controller("reserveCtrl", function ($scope, $rootScope, $http, $location) {
         $rootScope.pageName = "reserve";
         $scope.adultInputs = new Array(parseInt($rootScope.adults));
         $scope.childInputs = new Array(parseInt($rootScope.children));
         $scope.infantInputs = new Array(parseInt($rootScope.infants));
         $scope.adultsSize = function(num) {
              return $scope.adultInputs.length;
         }
         $scope.adultsSize = function(num) {
              return $scope.adultInputs.length;
         }
         $scope.childSize = function(num) {
                return $scope.childInputs.length;
         }
         $scope.infantSize = function(num) {
                 return $scope.infantInputs.length;
         }

         $scope.getNumber = function(num) {
             return new Array(parseInt(num));
         }

         $scope.changeAdults = function() {
              $scope.adultInputs = new Array(parseInt($scope.nadults));
         }

    });