/**
 *
 */
$(function () {


    $('button#get-started').click(function (event) {
        var request = new $.atmosphere.AtmosphereRequest();
        request.url = document.location.href + '/api/game/connect'
        request.transport = 'websocket';
        request.fallbackTransport = 'streaming';

        var socket = $.atmosphere.subscribe(request);

        $.atmosphere.unsubscribe();
    });


});
