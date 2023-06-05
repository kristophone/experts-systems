(defrule surgeon2-informs-chief-surgeon
    ?patient <- (patient-ready)
    ?assistant-nurse <- (assistant-nurse-ready)
    ?anesthesiologist <- (anesthesiologist-ready)
    ?chief-surgeon <- (chief-surgeon-ready)
    ?surgeon2 <- (surgeon2-ready)
  =>
  (retract ?patient ?assistant-nurse ?anesthesiologist ?chief-surgeon ?surgeon2)
  (printout t "Cirujano 2: La intervención puede comenzar." crlf)
  (assert (operation-started))
)

(defrule chief-surgeon-orders-anesthesiologist
  (operation-started)
  =>
  (printout t "Cirujano en Jefe: Anestesiólogo, confirma el cálculo del anestésico y aplícalo." crlf)
  (assert (anesthetic-calculated))
)

(defrule anesthesiologist-confirms-sedation
  (anesthetic-calculated)
  =>
  (printout t "Anestesiólogo: El paciente se encuentra sedado." crlf)
  (assert (patient-sedated))
)

(defrule chief-surgeon-orders-surgeon2
  (patient-sedated)
  =>
  (printout t "Cirujano en Jefe: Cirujano 2, puedes comenzar la intervención." crlf)
  (assert (surgeon2-starts-intervention))
)

(defrule surgeon2-requests-material
  (surgeon2-starts-intervention)
  =>
  (printout t "Cirujano 2: Enfermera asistente, por favor, provee el material e instrumentos necesarios." crlf)
  (assert (material-requested))
)

(defrule nurse-provides-material
  (material-requested)
  =>
  (printout t "Enfermera asistente: Aquí tienes el material e instrumentos necesarios." crlf)
  (assert (material-provided))
)

(defrule surgeon2-finishes-intervention
  (material-provided)
  =>
  (printout t "Cirujano 2: He finalizado la intervención." crlf)
  (assert (intervention-completed))
)

(defrule nurse-takes-patient-recovery-room
  (intervention-completed)
  =>
  (printout t "Enfermera asistente lleva al paciente a la Sala de recuperación." crlf)
  (assert (patient-recovery))
)

(defrule end
  (patient-recovery)
  =>
  (printout t "Intervención quirúrgica finalizada." crlf)
)


;;(assert (patient-ready) (assistant-nurse-ready) (anesthesiologist-ready) (chief-surgeon-ready) (surgeon2-ready))

;;(run)

