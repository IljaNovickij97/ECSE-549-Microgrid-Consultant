(deftemplate bar
    (slot ID)
    (slot length)
    (slot width)
    (slot height)
    )

(deftemplate square-cross-bar extends bar)
(deftemplate square-side-bar extends bar)
(deftemplate cube-bar extends bar)
(deftemplate unclassified-bar extends bar)


(deffacts bars
    (bar (ID "Pt-10") (length 20) (width 20) (height 20))
    (bar (ID "Pt-9") (length 30) (width 20) (height 10))
    (bar (ID "Cu-8") (length 10) (width 20) (height 30))
    (bar (ID "Fe-7") (length 10) (width 30) (height 20))
    (bar (ID "Fe-6") (length 30) (width 20) (height 10))
    (bar (ID "Fe-5") (length 30) (width 30) (height 10))
    (bar (ID "Pt-4") (length 10) (width 20) (height 10))
    (bar (ID "Cu-3") (length 20) (width 10) (height 20))
    (bar (ID "Cu-2") (length 20) (width 10) (height 10))
    (bar (ID "Fe-1") (length 10) (width 10) (height 10))
    )

(defrule square-cross-section
    (declare (salience 7))
    ?bar <- (bar {length == width})
    =>
    (assert (square-cross-bar (ID ?bar.ID)(length ?bar.length)(width ?bar.width)(height ?bar.height)))
)

(defrule square-side
    (declare (salience 6))
    ?bar <- (bar {length == height})
    =>
    (assert (square-side-bar (ID ?bar.ID)(length ?bar.length)(width ?bar.width)(height ?bar.height)))
)

(defrule cube
    (declare (salience 5))
    ?bar <- (bar {length == height && height == width})
    =>
    (assert (cube-bar (ID ?bar.ID)(length ?bar.length)(width ?bar.width)(height ?bar.height)))
)

(defrule unclassified
    (declare (salience 4))
    ?bar <- (bar {length != width && length != height && height != width})
    =>
    (assert (unclassified-bar (ID ?bar.ID)(length ?bar.length)(width ?bar.width)(height ?bar.height)))
    )

(defrule print-square-cross
    (declare (salience 3))
    ?bar <- (square-cross-bar)
    =>
    (printout t "Square-cross-section	" ?bar.ID"	" ?bar.length "	"  ?bar.width "	" ?bar.height crlf)
    )

(defrule print-square-side
    (declare (salience 2))
    ?bar <- (square-side-bar)
    =>
    (printout t "Square-side		" ?bar.ID"	" ?bar.length "	"  ?bar.width "	" ?bar.height crlf)
    )

(defrule print-cube
    (declare (salience 1))
    ?bar <- (cube-bar)
    =>
    (printout t "Cube			" ?bar.ID"	" ?bar.length "	"  ?bar.width "	" ?bar.height crlf)
    )

(defrule print-unclassified
    (declare (salience 0))
    ?bar <- (unclassified-bar)
    =>
    (printout t "Unclassified		" ?bar.ID"	" ?bar.length "	"  ?bar.width "	" ?bar.height crlf)
    )


(reset)
(printout t "Class			ID	Length	Width	Height" crlf)
(run)
