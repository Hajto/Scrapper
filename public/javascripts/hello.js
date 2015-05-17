function init() {
    function createOption(text) {
        var element = document.createElement("option");
        element.innerHTML = text;

        return element;
    }
    if(data.length > 0){
        for (var i = 0; i < data.length; i++) {
            document.getElementById("mainContainer").appendChild(createOption(data[i].name))
        }

        document.getElementById("mainContainer").addEventListener("change", function (event) {
            document.getElementById("data").innerHTML = data[event.target.selectedIndex].content
        });
        document.getElementById("data").innerHTML = data[0].content
    } else {
        document.getElementById("mainContainer").style.display = "none";
        document.writeln("Brak rekordów do wyświetlenia")
    }

}