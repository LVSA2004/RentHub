package br.com.fiap.alugueis.contrato;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.context.MessageSource;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/contrato")
public class ContratoController {

    @Autowired
    ContratoService service;

    @Autowired
    MessageSource message;
    
    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User user){
        model.addAttribute("username", user.getAttribute("name"));
        model.addAttribute("avatar_url", user.getAttribute("avatar_url"));
        model.addAttribute("contratos", service.findAll());
        return "contrato/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        if (service.delete(id)){
            redirect.addFlashAttribute("success", message.getMessage("contrato.delete.success", null, LocaleContextHolder.getLocale()));
        }else{
            redirect.addFlashAttribute("error",message.getMessage("contrato.notfound", null, LocaleContextHolder.getLocale()));
        }
        return "redirect:/contrato";
    }

    @GetMapping("new")
    public String form(Contrato contrato){
        return "contrato/form";
    }

    @PostMapping
    public String create(@Valid Contrato contrato, BindingResult binding, RedirectAttributes redirect){
        String title = contrato.getTitle();
        if (binding.hasErrors()) return "/contrato/form";
        try {
            PDDocument document = new PDDocument();

            for (int pageCounter = 1; pageCounter <= 6; pageCounter++) {
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.beginText();
				contentStream.newLineAtOffset(40, 800);
                
                if (pageCounter == 1) {
                    // Conteúdo da primeira página
					contentStream.showText("CONTRATO DE LOCAÇÃO");
                	contentStream.newLineAtOffset(0, -30);
                    contentStream.showText("DO OBJETO DA LOCAÇÃO");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    String[] textoloc = {
                        "( 1 ) - O presente contrato tem como OBJETO a locação do imóvel sito na Rua Engenheiro",
                        "Alexandre Machado, 77 casa 3, Vila Augusta – Guarulhos – SP, destinado exclusivamente",
                        "para fins residenciais."
                    };
                    for (String line : textoloc) {
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText(line);
                    }
					contentStream.newLineAtOffset(0, -30);
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
					contentStream.showText("DO PRAZO DE LOCAÇÃO");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    String[] textopra = {
                        "( 2 ) – O prazo de locação do imóvel acima descrito é de 24(vinte e quatro meses), iniciando",
                        "em 01 de Julho de 2021 e findando em 01 de Julho de 2023."
                    };
                    for (String line : textopra) {
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText(line);
                    }
					contentStream.newLineAtOffset(0, -30);
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
					contentStream.showText("DO ALUGUEL, DESPESAS, TAXAS, TRIBUTOS E OUTRAS DISPOSIÇÕES");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
					String[] texto= {
						"( 3 ) - O LOCATÁRIO pagará ao LOCADOR, a título de aluguel do imóvel em referência, o",
						"valor mensal de R$ 800,00 (oitocentos reais), o qual será reajustado consoante abaixo",
						"acertado no item 3.2. desta cláusula. O pagamento do aluguel inicial, se eventualmente não",
						"corresponder ao mês cheio, será cobrado “pro rata die” na data convencionada para pagamento.",
						"( 3.1.) – O aluguel e seus acessórios deverão ser pagos até o dia 07(sete) de cada mês, ou",
						"o primeiro dia útil, cuja cobrança de já fica acertada que será feita em dinheiro, fica acertado",
						"que o LOCATÁRIO obriga-se a providenciar o referido pagamento diretamente ao LOCADOR, ",
						"no endereço acima citado, independentemente de qualquer notificação prévia.",
						"( 3.2.) – O valor pago a título de retribuição da locação será reajustado anualmente, em",
						"consonância com a variação do IGP-M(Índice Geral de Preços de Mercado), ou outro que",
						"legalmente vier substituí-lo, perdurando por todo o período de vigência desta relação locatícia.",
						"( 3.3.) - Caso o LOCATÁRIO venha a efetuar o pagamento do aluguel através de cheque",
						"próprio, restará facultado ao LOCADOR emitir os recibos de pagamento somente após",
						"compensação positiva do mesmo. Os cheques utilizados em pagamento, se não",
						"compensados até o vencimento, ocasionarão mora do LOCATÁRIO, facultando ao",
						"LOCADOR a aplicação dos efeitos moratórios dispostos na cláusula de inadimplência.",
						"( 3.4.) – O pagamento de parcelas posteriores não significa a quitação de eventuais parcelas",
						"anteriores.",
						"( 3.5.) – O não pagamento dos aluguéis e acessórios nas datas aprazadas,",
						"independentemente de quaisquer aviso constituirá o LOCATÁRIO em mora."
					};
					for (String line : (texto)) {
						contentStream.newLineAtOffset(0, -25);
						contentStream.showText(line);
					}
                } else if (pageCounter == 2) {
                    // Conteúdo da segunda página
                    contentStream.newLineAtOffset(0, -10);
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    String[] textoCONT= {
						"( 3.6.) - O LOCATÁRIO, não vindo a efetuar o pagamento do aluguel até a data estipulada",
						"na Cláusula 3.1., ou não compensando o cheque destinado para tal fim, restará em mora,",
						"ficando obrigado a pagar multa de 10% (dez por cento) sobre o valor do aluguel previsto na",
						"cláusula 3 deste instrumento, bem como juros de mora de 3% ao mês, mais correção",
						"monetária e honorários advocatícios desde já estipulados em 20% (vinte por cento) sobre",
						"todo o saldo devedor, sem prejuízo da resolução deste contrato.",
						"( 3.7.) - Não configurarão novação ou adição às cláusulas contidas no presente instrumento,",
						"os atos de mera tolerâncias referentes ao atraso no pagamento do aluguel ou quaisquer",
						"outros tributos e despesas.",
						"( 3.8.) – Doravante são de responsabilidade exclusiva do LOCATÁRIO, e parte integrante",
						"deste instrumento contratual, as despesas com taxas, impostos e todos os encargos",
						"tributários municipais incidentes sobre o imóvel locado a partir de 01/07/2021. Havendo o",
						"LOCADOR eventualmente efetuado o pagamento dos referidos encargos, o LOCATÁRIO",
						"fica obrigado a reembolsar de imediato.",
						"( 3.9) – O LOCATÁRIO fica obrigado a transferir o nome das contas de água e luz,",
						"conforme o contrato.",
						"( 3.10.) – Os avisos de cobranças pertinentes ao imóvel locado, seja de tributos ou outros",
						"encargos, acaso recebidos pelo LOCATÁRIO, deverão ser entregues ao LOCADOR com a ",
						"antecedência necessária, sob pena de ser responsabilizado pelo pagamento dos encargos",
						"moratórios eventualmente gerados.",
						"( 3.11.) – As despesas de consumo de água e luz, na eventualidade de serem lançados fora",
						"do boleto bancário, são de responsabilidade exclusiva do LOCATÁRIO, inclusive respeitante",
						"aos encargos moratórios pela inadimplência."
					};
					for (String line : (textoCONT)) {
						contentStream.newLineAtOffset(0, -25);
						contentStream.showText(line);
					}
                } else if (pageCounter == 3) {
                    // Conteúdo da terceira página
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                    contentStream.showText("DAS CONDIÇÕES DO IMÓVEL");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    String[] textocon = {
                        "(4) – O LOCATÁRIO declara ter recebido o imóvel ora locado, com todas as benfeitorias e",
						"em perfeitas condições de uso, conservado, não havendo quaisquer avarias constatadas.",
						"( 4.1.) – O LOCATÁRIO obriga-se a manter o imóvel objeto deste contrato nas mesmas",
						"condições em que recebeu, correndo exclusivamente por sua conta todos os reparos,",
						"objetivando a conservação do dito imóvel, suas dependências, instalações e utensílios nele",
						"existentes, inclusive os consertos que se fizerem necessários na rede de água e esgoto, bem",
						"como as multas que der causa, por inobservância de quaisquer Leis, Decretos e",
						"Regulamentos da autoridade competente.",
						"( 4.2.) – É obrigação do LOCATÁRIO a conservação do imóvel, devendo mantê-lo em",
						"perfeito estado aparelhos sanitários, janelas, pisos, paredes, torneiras, sistemas hidráulico e",
						"elétrico, e assim devolvê-lo ao LOCADOR ao término da relação contratual, na mesma forma",
						"do quanto apresentado.",
						"( 4.3.) – Não sendo exercida eventual opção de compra, o LOCATÁRIO deverá restituir o",
						"imóvel, suas dependências, instalações e utensílios nele existentes, nas condições em que",
						"foram entregues, caso contrário, o aluguel e seus acessórios continuarão a correr até que o",
						"LOCATÁRIO cumpra todas as exigências do LOCADOR, entre as quais estão a de",
						"apresentar ao mesmo os comprovantes de pagamentos de consumo de água e luz com o",
						"seu pedido de desligamento, bem como de qualquer outro encargo de sua responsabilidade.",
						"( 4.4.) – Findo o prazo da locação, o LOCATÁRIO deverá restituir o imóvel locado",
						"inteiramente desocupado e no mesmo estado de conservação que o recebeu, apresentar",
						"todas as contas de consumo quitadas, sob pena de incorrer em multa por infração contratual,",
						"a qual prevista neste pacto."
                    };
                    for (String line : textocon) {
                        contentStream.newLineAtOffset(0, -25);
                        contentStream.showText(line);
                    }
				} else if (pageCounter == 4) {
                    // Conteúdo da terceira página
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                    contentStream.showText("DAS BENFEITORIAS");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    String[] textoben = {
                        "(5) – O LOCATÁRIO não poderá fazer no imóvel ora locado quaisquer obras ou benfeitorias,",
						"sejam elas úteis, necessárias ou voluptuárias, sem prévio e expresso consentimento do",
						"LOCADOR, manifestado por escrito, sob pena de incorrer em infração contratual.",
						"( 5.1.) – O LOCATÁRIO não terá direito de retenção ou indenização por quaisquer obras ou",
						"benfeitorias de qualquer espécie, mesmo que essas benfeitorias tenham o consentimento",
						"escrito do LOCADOR.",
						"( 5.2.) – Caso não convenha ao LOCADOR a permanência de qualquer benfeitoria ou",
						"modificação feitas pelo LOCATÁRIO no dito imóvel ou nas suas dependências, deverá este",
						"removê-las às suas custas, deixando o imóvel e suas dependências no estado em que se",
						"achavam antes da locação, correndo todas as despesas que para tal se fizerem necessário,",
						"por conta do LOCATÁRIO."
                    };
                    for (String line : textoben) {
                        contentStream.newLineAtOffset(0, -25);
                        contentStream.showText(line);
                    }
					contentStream.newLineAtOffset(0, -30);
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                    contentStream.showText("DA RESCISÃO");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    String[] textorec = {
                        "(6) - Ocorrerá a rescisão do presente contrato, independente de qualquer comunicação",
						"prévia ou indenização por parte do LOCATÁRIO, quando:",
							"a) Houver infração de qualquer cláusula deste contrato, ficando, neste caso, o",
							"LOCATÁRIO sujeito a multa de já estabelecida em valor correspondente a 3(três)",
							"meses de aluguéis vigentes à época de sua cobrança;",
							"b) Em hipótese de desapropriação do imóvel alugado;",
							"c) Caso o LOCATÁRIO não pague pontualmente qualquer das prestações assumidas;"
                    };
                    for (String line : textorec) {
                        contentStream.newLineAtOffset(0, -25);
                        contentStream.showText(line);
                    }
				} else if (pageCounter == 5) {
                    // Conteúdo da terceira página
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                    contentStream.showText("DA CAUÇÃO");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    String[] textocau = {
                        "(7) – Nos termos do que preceitua o art. 37, inciso I e art. 38, § 2º, da Lei do Inquilinato(Lei",
						"8.245/91), o LOCATÁRIO entrega ao LOCADOR, no ato da assinatura do presente pacto, a",
						"quantia de R$ 2.100,00 (dois mil e cem reais).",
						"(7.1.) – Pela presente o LOCATÁRIO autoriza o LOCADOR a utilizar, em caso de",
						"inadimplência contratual, total ou parcialmente, o valor dado em garantia para toda e",
						"qualquer eventualidade que se faça necessário recursos financeiros do LOCATÁRIO.",
						"(7.2.) – Finda a locação, com o correspondente distrato será autorizado o levantamento da",
						"quantia dada em garantia em favor do LOCATÁRIO, com os rendimentos legais (correção",
						"pela poupança), desde que observados os preceitos contratuais acertados entre as partes."
                    };
                    for (String line : textocau) {
                        contentStream.newLineAtOffset(0, -25);
                        contentStream.showText(line);
                    }
					contentStream.newLineAtOffset(0, -30);
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                    contentStream.showText("CONDIÇÕES GERAIS");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    String[] textoger = {
                        "(8) - As partes obrigam-se a informarem expressamente umas as outras eventuais mudanças",
						"de endereço, distinto pois do que ora fora informado para os fins contratuais e judiciais.",
						"(8.1.) - A cessão, transferência, empréstimo, sublocação ou comodato atinente ao imóvel",
						"objeto deste contrato, parciais ou totais, dependerão do prévio e expresso consentimento do",
						"LOCADOR, manifestado por escrito, sob pena de ação judicial de despejo do imóvel com",
						"retorno da posse do LOCADOR, na forma da Lei.",
						"(8.2.) – Fica avençado que o LOCADOR, pessoalmente ou por seu procurador constituído",
						"para tal finalidade, poderá examinar e vistoriar o imóvel locado, quando entender",
						"conveniente. No caso de pretensão de venda do imóvel, também fica acertado que os",
						"interessados poderão ter acesso ao imóvel locado, desde que tal procedimento seja feito em",
						"dia e hora estipulado previamente pelo LOCADOR.",
						"(8.3.) – O LOCATÁRIO deverá utilizar o imóvel de sorte a não interferir ao sossego e silêncio",
						"destinado aos seus vizinhos, obrigado-se, mais, por consequência, a cumprir normas e/ou",
						"regulamentos estabelecidos neste sentido.",
						"(8.4.) - Objetivando entregar o imóvel, restará como dever do LOCATÁRIO notificar o",
						"LOCADOR, ou seu representante legal, com antecedência mínima de 30(trinta) dias.",
						"(8.5.) – Fica acordado que a citação, notificação e/ou intimação, poderá ser feita pela via",
						"postal, com aviso de recebimento."
                    };
                    for (String line : textoger) {
                        contentStream.newLineAtOffset(0, -25);
                        contentStream.showText(line);
                    }
				} else if (pageCounter == 6) {
                    // Conteúdo da terceira página
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                    contentStream.showText("DO FORO");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    String[] textoforo = {
                        "(9) - Para dirimir quaisquer controvérsias oriundas do CONTRATO, as partes elegem o foro",
						"da Cidade de Guarulhos(SP).",
						"Por estarem assim justos e contratados, sem nenhum vício de consentimento, firmam",
						"o presente instrumento em 2 (duas) vias de igual teor, com 2 (duas) testemunhas",
						"instrumentárias."
                    };
                    for (String line : textoforo) {
                        contentStream.newLineAtOffset(0, -25);
                        contentStream.showText(line);
                    }
                }

                contentStream.endText();
                contentStream.close();
            }

            document.save(title +".pdf");
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        service.save(contrato);
        redirect.addFlashAttribute("success", message.getMessage("contrato.created.success", null, LocaleContextHolder.getLocale()));
        return "redirect:/contrato";
    }
    
}
