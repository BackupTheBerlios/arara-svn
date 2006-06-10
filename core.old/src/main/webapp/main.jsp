<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="functions" prefix="f"%>
      <div align="center">
        <table width="100%" border="0" cellspacing="2" height="100%">
          <tr>
            <td>
              <p>Sistema de banco de dados de imagens de aves do Brasil, vers�o 02.00</p>
              <hr>
              <p>Seja bem-vindo ao <b>Sistema de Informa��es de Aves do Brasil</b>. </p>
              <p>O objetivo deste projeto � disponibilizar um meio f�cil para que interessados em aves possam
              compartilhar suas fotos, ou visualizar fotos de outros autores, tornando o site um banco de dados
              de imagens das aves do Brasil.</p>
              <p>Sugest�es, cr�ticas e id�ias s�o muito bem-vindas. Envie seus coment�rios para o email 
              <a href="mailto:webmaster@aves.brasil.nom.br">webmaster</a></p>
              <b>O site segue a nomenclatura e a lista do CBRO </b>
              <br>
              <br>
              <br>
            
              <hr>
              <b>Usu�rios registrados: ${f:numberOfUsers()}</b><br>
              <b>Fotos no site: ${f:numberOfPhotos()}</b><br>
              <b>Fam�lias com fotos: ${f:numberOfFamilies()}</b><br>
              <b>Esp�cies com fotos: ${f:numberOfSpecies()}</b>
              <br>
              <br>
              <hr>
            </td>
          </tr>
          <tr>
            <td>
              <div align="center">
              <img src="/jsp/images/PicaPauzinho.jpg" width="160" height="107" align="bottom">
              <img src="/jsp/images/PicaPauzinho1.jpg" width="160" height="107" align="bottom">
              <img src="/jsp/images/PicaPauzinho2.jpg" width="160" height="107" align="bottom">
              </div>
            </td>
          </tr>
        </table>
      </div>
