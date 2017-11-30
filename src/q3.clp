(defrule gas-is-good
    (fuel-gauge ~empty)
    =>
    (assert(gas good))
    )

(defrule gas-is-bad
    (fuel-gauge empty)
    =>
    (assert (gas bad))
    (printout t "The gas tank is empty!" crlf)
    )

(defrule battery-is-good
    (car-lights good)
    =>
    (assert(battery good))
    )

(defrule battery-is-bad
    (car-lights bad)
    =>
    (assert(battery bad))
    (printout t "The battery is not working!" crlf)
    )

(defrule starter-motor-is-good
    (battery good) 
    (engine-turnover good)
    =>
    (assert(starter-motor good))
    )

(defrule starter-motor-is-bad
    (battery good)
    (engine-turnover bad)
    =>
    (assert(starter-motor bad))
    (printout t "The starter motor is not working!" crlf)
    )

(defrule oil-is-good
    (oil-dipstick full)
    =>
    (assert (oil good))
    )

(defrule oil-is-bad
    (oil-dipstick ~full)
    =>
    (assert (oil bad))
    (printout t "The car needs an oil replacement!" crlf)
    )

(defrule tyres-are-good
    (tyre-pressure ?pressure)
    (test (>= ?pressure 28.0))
    (test (<= ?pressure 32.0))
    =>
    (assert(tyres good))
    )

(defrule tyres-are-bad
    (tyre-pressure ?pressure)
    (or (test (< ?pressure 28.0)) (test (> ?pressure 32.0)))
    =>
    (assert(tyres bad))
    (printout t "Tyre pressure is not correct!" crlf)
    )

(defrule electrical-is-good
    (and (battery good) (starter-motor good))
    =>
    (assert(electrical good))
    )

(defrule electrical-is-bad
    (or (battery bad) (starter-motor bad))
    =>
    (assert(electrical bad))
    (printout t "The electrical system is faulty!" crlf)
    )

(defrule car-is-good
    (and (gas good) (oil good) (tyres good) (electrical good))
    =>
    (assert(car good))
    (printout t "Car should be working. Exiting program!" crlf)
    (clear)
    )

(defrule check-fuel-gauge
    (not (fuel-gauge ?))
    =>
    (printout t "What does the fuel gauge read?" crlf)
    (bind ?input (readline))
    (if (eq ?input "end")
        then
        (printout t "Exiting the program!" crlf)
        (clear)
     else
        (assert (fuel-gauge (sym-cat ?input)))
    )
)

(defrule check-car-lights
    (not (car-lights ?))
    =>
    (bind ?flag 0)
    (while (< ?flag 1)
        (printout t "Do the car-lights work (Y/N)?" crlf)
    	(bind ?input (readline))
        (if (eq ?input "end")
        then
        (printout t "Exiting the program!" crlf)
        (clear)
     elif (eq ?input "Y")
        then
        (assert (car-lights good))
        (bind ?flag 1)
     elif (eq ?input "N")
        then
        (assert (car-lights bad))
        (bind ?flag 1)
     else
        (printout t "Please enter a valid input. Either Y or N." crlf)
    	)
	)   
)

(defrule check-engine-turnover
    (not (engine-turnover ?))
    =>
    (bind ?flag 0)
    (while (< ?flag 1)
        (printout t "Does the engine turnover (Y/N)?" crlf)
    	(bind ?input (readline))
        (if (eq ?input "end")
        then
        (printout t "Exiting the program!" crlf)
        (clear)
     elif (eq ?input "Y")
        then
        (assert (engine-turnover good))
        (bind ?flag 1)
     elif (eq ?input "N")
        then
        (assert (engine-turnover bad))
        (bind ?flag 1)
     else
        (printout t "Please enter a valid input. Either Y or N." crlf)
    	)
	)   
)

(defrule check-oil-dipstick
    (not (oil-dipstick ?))
    =>
    (printout t "What does the oil dipstick indicate?" crlf)
    (bind ?input (readline))
    (if (eq ?input "end")
        then
        (printout t "Exiting the program!" crlf)
        (clear)
     else
        (assert (oil-dipstick (sym-cat ?input)))
    )
)

(defrule check-tyre-pressure
    (not (tyre-pressure ?))
    =>
    (printout t "What is the tyre pressure?" crlf)
    (bind ?input (readline))
    (if (eq ?input "end")
        then
        (printout t "Exiting the program!" crlf)
        (clear)
     else
        (assert (tyre-pressure (float ?input)))
    )
)


(reset)
(printout t "Type \"end\" at any point to exit the program!" crlf)
(run)
(printout t "Diagnostics complete" crlf)
(clear)









