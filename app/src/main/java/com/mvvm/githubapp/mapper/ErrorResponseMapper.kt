

package com.mvvm.githubapp.mapper

import com.mvvm.githubapp.model.ApiErrorResponse
import com.skydoves.sandwich.ApiErrorModelMapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message

/**
 * A mapper for mapping [ApiResponse.Failure.Error] response as custom [ApiErrorResponse] instance.
 *
 * @see [ApiErrorModelMapper](https://github.com/mvvm/sandwich#apierrormodelmapper)
 */
object ErrorResponseMapper : ApiErrorModelMapper<ApiErrorResponse> {

  /**
   * maps the [ApiResponse.Failure.Error] to the [ApiErrorResponse] using the mapper.
   *
   * @param apiErrorResponse The [ApiResponse.Failure.Error] error response from the network request.
   * @return A customized [ApiErrorResponse] error response.
   */
  override fun map(apiErrorResponse: ApiResponse.Failure.Error<*>): ApiErrorResponse {
    return ApiErrorResponse(apiErrorResponse.statusCode.code.toString(), apiErrorResponse.message())
  }
}
