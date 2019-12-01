function slide(direction) {
    var $current = $('.ads li.current');

    if (direction === "next") {
        var $next = $current.next().length ? $current.next() : $('.ads li:first');
    }
    else if (direction === "prev") {
        var $next = $current.prev().length ? $current.prev() : $('.ads li:last');
    }

    $current.removeClass('current');
    $current.fadeOut(500);

    $next.addClass('current');
    $next.delay(500).fadeIn(500);
}

function nextDiv() {
    clearInterval(autoplay);
    slide("next");
    autoplay = setInterval("slide('next')", 3500);
}

function prevDiv() {
    clearInterval(autoplay);
    slide("prev");
    autoplay = setInterval("slide('next')", 3500);
}

let autoplay = setInterval("slide('next')", 3500);