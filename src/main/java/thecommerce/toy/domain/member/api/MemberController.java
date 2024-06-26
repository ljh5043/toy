package thecommerce.toy.domain.member.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import thecommerce.toy.domain.member.payload.request.FindAllByPagingRequest;
import thecommerce.toy.domain.member.payload.request.ModifyMemberInfoRequest;
import thecommerce.toy.domain.member.payload.request.SaveNewMemberRequest;
import thecommerce.toy.domain.member.service.MemberService;
import thecommerce.toy.domain.member.validator.MemberValidator;
import thecommerce.toy.global.api.RestApiController;

import javax.validation.Valid;

@Api(tags = "MemberController", description = "회원 api")
@RestController
@RequestMapping(value = "/api/user")
@Slf4j
public class MemberController extends RestApiController {

    private final MemberService memberService;
    private final MemberValidator memberValidator;

    @InitBinder(value = "saveNewMemberRequest")
    public void initBinder(WebDataBinder binder) {binder.addValidators(memberValidator);}

    public MemberController(ObjectMapper objectMapper, MemberService memberService, MemberValidator memberValidator) {
        super(objectMapper);
        this.memberService = memberService;
        this.memberValidator = memberValidator;
    }

    @Operation(summary = "신규 회원 생성")
    @PostMapping(value = "/join")
    public ResponseEntity<String> join(@RequestBody @Valid SaveNewMemberRequest request,
                                       BindingResult bindingResult) {
        return createRestResponseWithCreated(memberService.saveNewMember(request));
    }

    @Operation(summary = "회원 목록 조회")
    @GetMapping(value = "/list")
    public ResponseEntity<String> list(@ModelAttribute @Valid FindAllByPagingRequest request,
                                       BindingResult bindingResult) {
        return createRestResponse(memberService.findAllByPaging(request));
    }

    @Operation(summary = "회원 정보 수정")
    @PostMapping(value = "/{loginId}")
    public ResponseEntity<String> update(@PathVariable String loginId,
                                         @RequestBody @Valid ModifyMemberInfoRequest request,
                                         BindingResult bindingResult) {
        return createRestResponse(memberService.update(loginId, request));
    }

}
