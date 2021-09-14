const menuLinks = [...document.querySelectorAll('.scroll-to-link')];
const btn_comments = document.getElementById("btn-comments");
const btn_reviews = document.getElementById("btn-reviews");
const loader = document.createElement("div");
loader.classList.add("spinner-3");

btn_comments.addEventListener("click",function(e){
    executeAPI("comments")
})

btn_reviews.addEventListener("click",function(e){
    executeAPI("reviews")
})

menuLinks.forEach((e)=>{
    e.addEventListener("click",function(e){
        e.preventDefault();
        const closest = e.target.closest(".scroll-to-link");
        menuLinks.forEach((e)=>{
            e.classList.remove("active");
        })
        closest.classList.add("active");
        const headingId = closest.dataset.target;
        const element = document.getElementById(`content-${headingId}`);
        element.scrollIntoView({behavior:"smooth"})
    })
})
function executeAPI(type){
    const element = document.getElementById(`${type}-json`);
    element.innerText="";
    element.appendChild(loader);
    fetch(`https://spring-json.herokuapp.com/${type}?num=10`).then((result) => result.json()).then((res) => {
    element.removeChild(loader);
    element.style.minHeight="30px";
    element.append(renderjson(res));
    console.log(res)
}).catch(err => console.log('error!'));
}


