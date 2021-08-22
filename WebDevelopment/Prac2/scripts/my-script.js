let questionNum = 0;
let correctAnswer = 0;
function init()
{
    document.querySelector("#results").style.display = "none"; // hide result div 
    document.querySelector('b').innerText = getUrlParam("name");
    getQuestion();
    document.querySelector('#next').addEventListener("click", next);
}

init();


function getQuestion(){
    document.querySelectorAll('p')[1].innerText = questions[questionNum]["question"];
    document.querySelector("#choice-A").innerText = questions[questionNum]["choiceA"];
    document.querySelector("#choice-B").innerText = questions[questionNum]["choiceB"];
    document.querySelector("#choice-C").innerText = questions[questionNum]["choiceC"];
    // the display question number 
    document.querySelectorAll('p')[0].innerText = `Question ${questionNum +1}`;
}

function next(event){
   
    // check correctness
    if (getSelection("choices") === questions[questionNum]["answer"]){
        correctAnswer++;
    }

    // question display and 'next' button hidding effect
    questionNum++;
    if (questionNum < questions.length){
        getQuestion();
        clearSelection("choices");
    } else {
        event.target.style.display = "none";
        showResult();
    }
}

function showResult(){
    let percentage;
    percentage = ((correctAnswer/questions.length)*100).toFixed(2); // set to 2 decimal number
    const result = document.querySelector('#results p'); 
    if (percentage < 30){
        result.innerText = `Bad luck. Your final score was ${percentage}% (${correctAnswer}/${questions.length})`;
    } else if (percentage <= 75){
        result.innerText = `Not bad. Your final score was ${percentage}% (${correctAnswer}/${questions.length})`;
    } else {
        result.innerText = `Impressive. Your final score was ${percentage}% (${correctAnswer}/${questions.length})`;
    }
    
    // hide quiz div
    document.querySelector("#quiz").style.display = "none";
    // show result div
    document.querySelector("#results").style.display = "block";
}