(deftemplate check
    (slot checked)
    )

(defrule walking
    (walk-sign walk)
    =>
    (assert (status walking))
    )

(defrule driving
    (walk-sign dont-walk)
    =>
    (assert(status driving))
    )

(defrule light-red-driving
    (status driving)
    (or (light red) (light blinking-red))
    =>
    (printout t "Stop driving!" crlf)
    )

(defrule light-green-driving
    ?check <- (check {checked == no})
    (status driving)
    (or (light green) (light blinking-green))
    =>
    (printout t "Keep driving!" crlf)
    (modify ?check (checked 123.0));
    )

(defrule light-yellow-driving
    (status driving)
    (or (light yellow) (light blinking-yellow))
    =>
    (printout t "Prepare to stop driving!" crlf)
    )

(defrule light-red-walking
    (status walking)
    (or (light red) (light blinking-red))
    =>
    (printout t "Stop walking!" crlf)
    )

(defrule light-green-walking
    (status walking)
    (or (light green) (light blinking-green))
    =>
    (printout t "Keep walking!" crlf)
    )

(defrule light-yellow-walking
    (status driving)
    (or (light yellow) (light blinking-yellow))
    =>
    (printout t "Prepare to stop walking!" crlf)
    )
