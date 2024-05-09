package visao

import modelo.Campo
import modelo.CampoEvento
import java.awt.Color
import java.awt.Font
import javax.swing.BorderFactory
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.SwingUtilities

private val COR_BG_NORMAL = Color(126, 76, 23)
private val COR_BG_MARCACAO = Color(225, 225, 225)
private val COR_BG_EXPLOSAO = Color(225, 225, 225)
private val COR_TXT_VERDE = Color(0, 0, 0)

class BotaoCampo(private val campo: Campo) : JButton() {

    init {
        icon = null
        font = font.deriveFont(Font.BOLD)
        background = COR_BG_NORMAL
        isOpaque = true
        border = BorderFactory.createBevelBorder(0)
        addMouseListener(MouseCliqueListener(campo, { it.abrir() }, { it.alterarMarcacao() }))

        campo.onEvento(this::aplicarEstilo)
    }

    private fun aplicarEstilo(campo: Campo, evento: CampoEvento) {
        when(evento) {
            CampoEvento.EXPLOSAO -> aplicarEstiloExplodido()
            CampoEvento.ABERTURA -> aplicarEstiloAberto()
            CampoEvento.MARCACAO -> aplicarEstiloMarcado()
            else -> aplicarEstiloPadrao()
        }

        SwingUtilities.invokeLater {
            repaint()
            validate()
        }
    }

    private fun aplicarEstiloExplodido() {
        background = COR_BG_EXPLOSAO
        val icon = ImageIcon(javaClass.getResource("/images/explode1.png"))
        setIcon(icon);
    }

    private fun aplicarEstiloAberto() {
        background = COR_BG_NORMAL
        border = BorderFactory.createLineBorder(Color.GRAY)

        foreground = when (campo.qtdeVizinhosMinados) {
            1 -> COR_TXT_VERDE
            2 -> Color.BLUE
            3 -> Color.YELLOW
            4, 5, 6 -> Color.RED
            else -> Color.PINK
        }

        text = if (campo.qtdeVizinhosMinados > 0) campo.qtdeVizinhosMinados.toString() else ""
    }

    private fun aplicarEstiloMarcado() {
        background = COR_BG_MARCACAO
        val icon = ImageIcon(javaClass.getResource("/images/flag.png"))
        setIcon(icon);
    }

    private fun aplicarEstiloPadrao() {
        background = COR_BG_NORMAL
        border = BorderFactory.createBevelBorder(0)
        text = ""
        icon = null
    }
}