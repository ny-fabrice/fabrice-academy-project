(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    // Find all random quote components
    var quoteComponents = document.querySelectorAll(".random-quote");

    function refreshQuote(component) {
      var path = component.getAttribute("data-component-path");
      if (!path) {
        return;
      }

      fetch(path + ".html")
        .then(function (response) {
          return response.text();
        })
        .then(function (html) {
          var temp = document.createElement("div");
          temp.innerHTML = html;

          var newQuote = temp.querySelector(".random-quote__container");
          var oldQuote = component.querySelector(".random-quote__container");

          if (newQuote && oldQuote) {
            oldQuote.innerHTML = newQuote.innerHTML;
          }
        })
        .catch(function (error) {
          console.log("Error refreshing quote:", error);
        });
    }

    quoteComponents.forEach(function (component) {
      var autoRefresh = component.getAttribute("data-auto-refresh") === "true";
      var interval =
        parseInt(component.getAttribute("data-refresh-interval"), 10) * 1000;

      // Wire "New Random Quote" button for AJAX refresh
      var button = component.querySelector(".random-quote__refresh");
      if (button) {
        button.addEventListener("click", function () {
          refreshQuote(component);
        });
      }

      if (autoRefresh && interval > 0) {
        // Auto-refresh the quote by reloading the component fragment
        setInterval(function () {
          refreshQuote(component);
        }, interval);
      }
    });
  });
})();
