function init() {
    function createOption(text) {
        var element = document.createElement("option");
        element.innerHTML = text;

        return element;
    }
    if(data.length > 0){
        for (var i = 0; i < data.length; i++) {
            document.getElementById("mainContainer").appendChild(createOption(data[i].name));

            var table = $("<table>")
                .addClass("table table-stripped")
                .css({
                    width: "100%",
                    textAlign: "center"
                });

            for(var rows = 0; rows < data[i].content.length-1; rows++){
                var row = $("<tr>")
                    .append($("<td>").html(data[i].content[rows].hour))
                    .append($("<td>").html(data[i].content[rows].who))
                    .append($("<td>").html(data[i].content[rows].age));
                $(table).append(row);
            }
            console.log(table);
            parsed.push(table)
        }

        document.getElementById("mainContainer").addEventListener("change", function (event) {
            $("#data").empty().append(parsed[event.target.selectedIndex])
        });
        $("#data").empty().append(parsed[0])
    } else {
        document.getElementById("mainContainer").style.display = "none";
        document.writeln("Brak rekordów do wyświetlenia")
    }

}