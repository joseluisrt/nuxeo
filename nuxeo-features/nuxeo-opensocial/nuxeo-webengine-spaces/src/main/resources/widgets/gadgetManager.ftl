<div>
<#if nxthemesInfo.model.anonymous == false>
  <div id="getGadgetManager">
    <a href="#" id="openGadgetManager">Ajouter un gadget</a>
  </div>
  <div id="gadgetManager" style="display:none;">

    <div id="closeGadgetManager">
      <a href="#" id="closeLinkGadgetManager">Fermer</a>
    </div>
    <div id="gadgetManagerContainer">
      <div id="listCategories">
      <#if nxthemesInfo.model.categories>
      <#list nxthemesInfo.model.categories as category>
          <div class="button-container">
              <!--button class="nv-category" category="${category}">${category}</button-->
              <a class="nv-category" category="${category}">${category}</a>
          </div>
       </#list>
      </#if>
      </div>
      <div id="listGadgets">
        <ul class="option" id="tabGadgets">
        <#if nxthemesInfo.model.gadgets>
        <#list nxthemesInfo.model.gadgets as gadget>
          <li class="invisible">
            <div class="nameGadget">${gadget.name}</div>
            <a href="#_" name="${gadget.name}" class="typeGadget" category="${gadget.category}" style="background-image:url(${gadget.iconUrl})"></a>
            <div class="directAdd">
              <a href="#" class="directAddLink linkAdd" name="${gadget.name}">
                  <span class="addGadgetButton">ajouter</span>
              </a>
          </div>
          </li>
       </#list>
       </#if>
      </ul>
      </div>
    </div>

  </div>
</#if>
</div>