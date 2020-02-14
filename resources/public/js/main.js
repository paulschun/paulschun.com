document.addEventListener('DOMContentLoaded', function() {
  // Get all "navbar-burger" elements
  const $navbarBurgers = Array.prototype.slice.call(document.querySelectorAll('.navbar-burger'), 0);

  // Check if there are any navbar burgers
  if ($navbarBurgers.length > 0) {

    // Add a click event on each of them
    $navbarBurgers.forEach( function(el) {
      el.addEventListener('click', function() {

        // Get the target from the "data-target" attribute
        const target = el.dataset.target;
        const $target = document.getElementById(target);

        // Toggle the "is-active" class on both the "navbar-burger" and the "navbar-menu"
        el.classList.toggle('is-active');
        $target.classList.toggle('is-active');

      });
    });
  }
});

document.addEventListener('DOMContentLoaded', function() {
  const $hireMeButtons = Array.prototype.slice.call(document.querySelectorAll('.hire-me'), 0);

  if ($hireMeButtons.length > 0) {

    $hireMeButtons.forEach( function(el) {
      el.addEventListener('click', function() {

        const target = el.dataset.target;
        const $target = document.getElementById(target);

        el.classList.toggle('is-active');
        $target.classList.toggle('is-active');

      });
    });
  }
});

document.addEventListener('DOMContentLoaded', function() {
  const $modalCloseButtons = Array.prototype.slice.call(document.querySelectorAll('.modal-close-action'), 0);
  if ($modalCloseButtons.length > 0) {
    $modalCloseButtons.forEach( function(el) {
      el.addEventListener('click', function() {
        const $targets = Array.prototype.slice.call(document.querySelectorAll('.modal'), 0);
        if ($targets.length > 0) {
          $targets.forEach(function(target) {
            target.classList.remove('is-active');
          });
        }
      });
    });
  }
});
