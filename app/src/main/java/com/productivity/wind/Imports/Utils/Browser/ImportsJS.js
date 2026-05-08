
        window.log = function(msg) {
           console.log("[WINDWEB_LOG] " + msg);
        };
        
        window.findContainerHTML = function(el) {
           let current = el;
           let best = el;
           
           while (current && current !== document.body) {
              const style = window.getComputedStyle(current);
              
              const hasLayout =
                 style.display !== "inline" &&
                 style.visibility !== "hidden";
              
              const hasChildren =
                 current.children && current.children.length >= 2;
                 
              const hasContent =
                 (current.innerText || "").trim().length > 10;
                 
              const isBigEnough =
                 current.getBoundingClientRect().height > 20;
                 
              // score = "this looks like a container"
              if (hasLayout && hasChildren && hasContent && isBigEnough) {
                 best = current;
              }
              current = current.parentElement;
           }
           return best;
        }

