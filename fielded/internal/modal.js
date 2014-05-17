function runModal(placeholder, getcompletionsfunction, cssclass, initialText) {
    "use strict";
    let modal = $("<dialog class='" + cssclass + "'><input spellcheck='false' data-autosize-input='{ \"space\": 10 }' autocomplete='off' placeholder='" + placeholder + "' class='Field-textBox' type='text' name='main'></input><ol></ol></dialog>")

    modal.appendTo($("body"))

    var inputBox = modal.find("input")
    var ol = modal.find("ol")

    if (initialText)
        inputBox.val(initialText)

    console.log()

    function updateCompletions() {
        $(ol).empty()
        console.log("text is currently " + inputBox.val())
        var completions = getcompletionsfunction(inputBox.val())
        console.log(completions)

        for (var i = 0; i < completions.length; i++) {
            var num = ""
            if (i == 0) {
                num = "&crarr;"
            } else if (i < 9) {
                num = "^" + i
            }
            var label = $("<li><span class='Field-number'>" + num + "</span>" + completions[i].text + "</li>")
            let callback = completions[i].callback
            label.hover(function () {
                $(this).css("background", "#444")
            }, function () {
                $(this).css("background", "")
            })
            label.click(function () {
                callback(inputBox.val())
                modal[0].close()
                modal.detach()
                setTimeout(function () {
                    cm.focus()
                }, 25)
            })

            ol.append(label)
        }
        return completions
    }


    updateCompletions()

    inputBox.on("input", function (x) {
        console.log(this.value)
        updateCompletions()
    })

    function highlightRunAndClose(value, index, event) {
        var cc = updateCompletions(value)
        if (cc.length > index) {
            cc[index].callback(value)
            $($(ol).children("li")[index]).css("background", "#555")
            event.preventDefault()

            setTimeout(function () {
                modal[0].close()
                modal.detach()
                cm.focus()
            }, 25)
        }

        setTimeout(function () {
            cm.focus()
        }, 25)
        cm.focus()

    }

    inputBox.on("keydown", function (x) {
        console.log(x.keyCode)
        if (x.keyCode == 13) {
            highlightRunAndClose(this.value, 0, x)
        }
        if (x.keyCode == 27) {
            console.log("forcing focus?")
            setTimeout(function () {
                cm.focus()
            }, 25)
        }
        if (x.keyCode > 48 && x.keyCode < 48 + 10 && x.ctrlKey) {
            highlightRunAndClose(this.value, x.keyCode - 48, x)
        }
    })

    modal[0].showModal()

    $("input[data-autosize-input]").autosizeInput()

    $(modal[0]).width($($(modal[0]).children()[1]).width())

    return modal
}


//runModal("running modal...", completme, "Field-modal")

function runModalAtCursor(placeholder, completeme, initialText) {
    var m = runModal(placeholder, completeme, "Field-modal-positioned", initialText)
    var cc = cm.cursorCoords()
    console.log(cc)
    if (cc.bottom > $(window).height() / 2)
        $(m).css("bottom", $(window).height() - cc.bottom)
    else
        $(m).css("top", cc.bottom)
    $(m).css("left", cc.right)
}


//runModalAtCursor("banana", completme)