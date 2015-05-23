val thig = "<tr >                <td ></td>                <td></td>                <td ></td>              </tr> "
println(thig.replaceAll("""<tr\s?>(<td\s?>\s?</\s?td>)</\s?tr>"""," debug"))

thig.replaceAll("""<td\s?>\s?</\s?td>""","d")