
/*** Please write your JavaScript in my-script.js ***/

const questions = [
    {
        question: "A popular code editor for web development is...",
         choiceA: "Word",
         choiceB: "Visual Studio Code",
         choiceC: "Photoshop",
          answer: "B",
    },
    {
        question: "What does the abbreviation JS stand for?",
         choiceA: "Hypertext Markup Language",
         choiceB: "Cascading Style Sheets",
         choiceC: "JavaScript",
          answer: "C",
    },
    {
        question: "Which company makes the Chrome browser?",
         choiceA: "Google",
         choiceB: "Mozilla",
         choiceC: "Microsoft",
          answer: "A",
    },
];

function getSelection(name)
{
    const elem = document.querySelector(`input[name="${name}"]:checked`);
    return elem ? elem.value : "";
}

function clearSelection(name)
{
    const elem = document.querySelector(`input[name="${name}"]:checked`);
    if (elem)
        elem.checked = false;
}

function getUrlParam(name)
{
    const params = new URLSearchParams(window.location.search);
    return params.has(name) ? params.get(name) : "";
}
