var app = angular.module("myApp", ["ngRoute"]);
app.filter('city', function() {
  return function(name) {
    if (name == "MHD") {
      return "مشهد";
    } else if (name == "THR") {
      return "تهران";
    } else {
      return name;
    }
  }
});
app.filter('rial', function() {
  return function(amount) {
    return amount + " " + "ریال";
  }
});
app.filter('time', function() {
  return function(time) {
    var res = "";
    for (var i = 0; i < time.length; i++) {
      if (i == 2)
        res += ":";
      switch (time[i]) {
        case "0":
          res += "۰";
          break;
        case "1":
          res += "۱";
          break;
        case "2":
          res += "۲";
          break;
        case "3":
          res += "۳";
          break;
        case "4":
          res += "۴";
          break;
        case "5":
          res += "۵";
          break;
        case "6":
          res += "۶";
          break;
        case "7":
          res += "۷";
          break;
        case "8":
          res += "۸";
          break;
        case "9":
          res += "۹";
          break;
      }
    }
    return res;
  }
});

app.config(function($routeProvider) {
  $routeProvider
    .when("/", {
      templateUrl: "search.html",
      controller: "searchCtrl",
      css: "search.css"
    }).when("/result", {
      templateUrl: "result.html",
      controller: "resultCtrl",
      css: "result.css"
    }).when("/reserve", {
      templateUrl: "reserve.html",
      controller: "reserveCtrl",
      css: "reserve.css"
    }).when("/tickets", {
      templateUrl: "tickets.html",
      controller: "ticketsCtrl",
      css: "tickets.css"
    }).when("/loading", {
      templateUrl: "loading.html"
    }).when("/auth", {
      templateUrl: "loginForm.html",
      controller: "authCtrl",
      css: "login.css"
    });
});


app.controller('authCtrl', function($scope, $rootScope, $http, $location, $route) {
  $rootScope.CSS = $route.current.$$route.css;
  $scope.login = function() {
    var body = {
      username: $scope.username,
      password: $scope.password
    };

    $http.post('http://localhost:8080/ali/booking/login', body)
      .then(function(response) {
        if (response.data.status == "failed") {
            alert("نام کاربری یا رمز عبور نامعتبر")
        } else {
          $rootScope.token = response.data.token;
          alert($rootScope.token);
        }

        // $location.url('/result');
      });
  };
})

app.controller("searchCtrl", function($scope, $rootScope, $http, $location, $route) {
  $rootScope.CSS = $route.current.$$route.css;
  console.log($rootScope.CSS);
  $rootScope.pageName = "search";
  $scope.searchFlights = function() {
    var body = {
      src: $scope.source,
      dest: $scope.dest,
      startDate: $scope.date,
      endDate: $scope.date2,
      numOfAdults: $scope.adults,
      numOfChildren: $scope.children,
      numOfInfants: $scope.infants
    };
    console.log(body);

    $location.url('/loading');
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

app.controller("resultCtrl", function($scope, $rootScope, $http, $location, $route) {
  $rootScope.CSS = $route.current.$$route.css;
  console.log($scope.sortVar);
  console.log($rootScope.CSS);
  $scope.reserveFlight = function(index) {
    console.log(index);
    $rootScope.flight = $rootScope.flights[index];
    var body = {
      arrivalTime: $rootScope.flights[index].arrivalTime,
      departureTime: $rootScope.flights[index].departureTime,
      flightNumber: $rootScope.flights[index].flightNumber,
      airlineCode: $rootScope.flights[index].airlineCode,
      planeModel: $rootScope.flights[index].planeModel,
      dest: $rootScope.flights[index].dest,
      seatClassName: $rootScope.flights[index].seatClassName,
      origin: $rootScope.flights[index].origin,
      date: $rootScope.flights[index].date,
      numOfInfants: $rootScope.infants,
      numOfChildren: $rootScope.children,
      numOfAdults: $rootScope.adults
    }
    console.log(body);
    $http.post('http://localhost:8080/ali/booking/reserve', body)
      .then(function(response) {
        $rootScope.adultSum = response.data.adultSum;
        $rootScope.infantSum = response.data.infantSum;
        $rootScope.childSum = response.data.childSum;
        $rootScope.totalSum = response.data.totalSum;
        $rootScope.adultFee = response.data.adultFee;
        $rootScope.childFee = response.data.childFee;
        $rootScope.infantFee = response.data.infantFee;
        $location.url('/reserve');
      });
  }
  $scope.changeOrder = function() {
    console.log($scope.order);
    // $scope.order = !$scope.order;
    // console.log($scope.order);
  }
  $scope.checkSelected = function(flight) {
    if (flight.airlineCode == $scope.showAirline || $scope.showAirline == "all")
      if ($scope.showClass == "all" || flight.seatClassName == $scope.showClass)
        return true;
    return false;
  }

});

app.controller("reserveCtrl", function($scope, $rootScope, $http, $location, $route) {
  $rootScope.CSS = $route.current.$$route.css;
  console.log($rootScope.CSS);
  $rootScope.pageName = "reserve";

  // $rootScope.adults = 1;
  // $rootScope.children = 1;
  // $rootScope.infants = 1;

  $scope.numAdults = $rootScope.adults;
  $scope.numChildren = $rootScope.children;
  $scope.numInfants = $rootScope.infants;

  $scope.stableNumInfants = $scope.numInfants;
  $scope.stableNumChildren = $scope.numChildren;
  $scope.stableNumAdults = $scope.numAdults;


  $scope.adultsGenders = new Array(parseInt($scope.numAdults));
  $scope.adultsNames = new Array(parseInt($scope.numAdults));
  $scope.adultsSurnames = new Array(parseInt($scope.numAdults));
  $scope.adultsIds = new Array(parseInt($scope.numAdults));

  $scope.childrenGenders = new Array(parseInt($scope.numChildren));
  $scope.childrenNames = new Array(parseInt($scope.numChildren));
  $scope.childrenSurnames = new Array(parseInt($scope.numChildren));
  $scope.childrenIds = new Array(parseInt($scope.numChildren));

  $scope.infantsGenders = new Array(parseInt($scope.numInfants));
  $scope.infantsNames = new Array(parseInt($scope.numInfants));
  $scope.infantsSurnames = new Array(parseInt($scope.numInfants));
  $scope.infantsIds = new Array(parseInt($scope.numInfants));

  $scope.submitPassengers = function(isValid) {
    if (parseInt($scope.numAdults) + parseInt($scope.numChildren) + parseInt($scope.numInfants) > 4) {
      alert("تعداد صندلی‌ها بیش از صندلی‌های موجود\n" + "تعداد صندلی‌های موجود: " + 4);
      return;
    }
    if (!isValid) {
      alert("داده‌های ورودی نامعتبر");
      return;
    }
    var people = [];
    for (var i = 0; i < $scope.numAdults; i++) {
      people.push({
        firstName: $scope.adultsNames[i],
        surName: $scope.adultsSurnames[i],
        nationalId: $scope.adultsIds[i],
        ageType: "adult",
        gender: $scope.adultsGenders[i]
      });
    }

    for (var i = 0; i < $scope.numChildren; i++) {
      people.push({
        firstName: $scope.childrenNames[i],
        surName: $scope.childrenSurnames[i],
        nationalId: $scope.childrenIds[i],
        ageType: "child",
        gender: $scope.childrenGenders[i]
      });
    }

    for (var i = 0; i < $scope.numInfants; i++) {
      people.push({
        firstName: $scope.infantsNames[i],
        surName: $scope.infantsSurnames[i],
        nationalId: $scope.infantsIds[i],
        ageType: "infant",
        gender: $scope.infantsGenders[i]
      });
    }

    console.log(people);

    var body = {
      arrivalTime: $rootScope.flight.arrivalTime,
      departureTime: $rootScope.flight.departureTime,
      flightNumber: $rootScope.flight.flightNumber,
      airlineCode: $rootScope.flight.airlineCode,
      planeModel: $rootScope.flight.planeModel,
      dest: $rootScope.flight.dest,
      seatClassName: $rootScope.flight.seatClassName,
      origin: $rootScope.flight.origin,
      date: $rootScope.flight.date,
      numOfInfants: $rootScope.infants,
      numOfChildren: $rootScope.children,
      numOfAdults: $rootScope.adults,
      people: people
    }
    console.log(body);
    $http.post('http://localhost:8080/ali/booking/finalize', body)
      .then(function(response) {
        console.log(response.data);
        $rootScope.tickets = response.data.tickets;
        $location.url('/tickets');
      });
  }

  $scope.makeArray = function(num) {
    var r = new Array(parseInt(num));
    return r;
  }
});


app.controller("ticketsCtrl", function($scope, $rootScope, $http, $location, $route) {
  $rootScope.CSS = $route.current.$$route.css;
  console.log($rootScope.CSS);
});