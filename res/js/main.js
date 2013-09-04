$(document).ready(function(){

    var searchbox = 'input#searchbox';
    var searchres = 'div#previous ul.squarebullets';

    // call some AJAX that searches when key goes down
    $(searchbox).keyup(function(){

        var term = $(searchbox).val();

        if( term != null && $.trim(term) != "" ){
            do_ajax_search(term);
            $(searchbox).focus();
        }

    });

    // do the ajax call and populate searchresult
    function do_ajax_search(query){
        $.ajax({
            type: 'GET',
            url:  '/mb/ajax',
            data: { q: query },
            success:function(data){
                $(searchres).empty();
                $(searchres).append(data);
            },
            error:function(data){
                $(searchres).empty();
            },
        });
    }

});
