(function() {
  "use strict";

  function initHeroSliders() {
    if (typeof Swiper === "undefined") {
      return;
    }
    var containers = document.querySelectorAll(".hero-slider-container");
    containers.forEach(function(container) {
      var swiperEl = container.querySelector(".swiper");
      if (!swiperEl || swiperEl.swiper) {
        return;
      }
      var autoplaySpeed = parseInt(swiperEl.dataset.autoplay, 10) || 5000;
      var showNavigation = swiperEl.dataset.navigation === "true";
      var paginationEl = swiperEl.querySelector(".swiper-pagination");
      var nextEl = swiperEl.querySelector(".swiper-button-next");
      var prevEl = swiperEl.querySelector(".swiper-button-prev");

      new Swiper(swiperEl, {
        loop: true,
        autoplay: {
          delay: autoplaySpeed,
          disableOnInteraction: false
        },
        pagination: paginationEl ? { el: paginationEl, clickable: true } : false,
        navigation: showNavigation && nextEl && prevEl
          ? { nextEl: nextEl, prevEl: prevEl }
          : {},
        effect: "fade",
        fadeEffect: { crossFade: true },
        speed: 1000
      });
    });
  }

  function runInit() {
    if (document.readyState === "loading") {
      document.addEventListener("DOMContentLoaded", initHeroSliders);
    } else {
      initHeroSliders();
    }
    window.addEventListener("load", initHeroSliders);
    setTimeout(initHeroSliders, 300);
  }
  runInit();
})();
