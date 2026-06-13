this.jsGlobalFun("log",
        """
        function(...args) {
           const msg = args
              .map(a => String(a))
              .join(" ")
              .replace(/\n/g, " | ");
                 
            console.log("[WINDWEB_LOG]", msg);
        };
        """
    )

    
    this.jsGlobalFun("findContainerHTML",
        """
        function(el, maxSteps = 3) {
           let current = el;

           for (let i = 0; i < maxSteps; i++) {
              if (!current || current === document.body) break;
              current = current.parentElement;
           }

           return current || el;
        };
        """
    )
    this.jsGlobalFun("webItemUrl",
        """
        function(el, depth = 3) {
            let cur = el;
            const stack = [];

            for (let i = 0; i < depth; i++) {
                if (!cur || cur === document.body) break;
                cur = cur.parentElement;
                if (!cur) break;

                stack.push(cur.tagName + ", " + (cur.className || ""));
            }

            return stack.reverse().join(" / ");
        };
        """
    )
    

    this.jsGlobalFun("unique",
        """
        function(list, getKey) {
            const arr = Array.from(list || []);
            const seen = new Set();

            return arr.filter(item => {
                const key = getKey ? getKey(item) : item;
                if (seen.has(key)) return false;
                seen.add(key);
                return true;
            });
        };
        """
    )
    this.jsGlobalFun("hide",
        """
        function(el) {
            if (el) {
               el.style.display = "none"
            }
        };
        """
    )
