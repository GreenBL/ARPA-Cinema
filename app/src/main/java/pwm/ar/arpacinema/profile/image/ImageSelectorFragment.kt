package pwm.ar.arpacinema.profile.image

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import pwm.ar.arpacinema.databinding.FragmentImageSelectorBinding
import pwm.ar.arpacinema.model.ProfileImage

class ImageSelectorFragment : Fragment() {

    // test
    private val testingData = List(13) {
        ProfileImage(1, "https://picsum.photos/5000/5000?random")
    }

    private var _binding : FragmentImageSelectorBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ImageSelectorFragment()
    }

    private val viewModel: ImageSelectorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageSelectorBinding.inflate(inflater, container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val imageList : List<ProfileImage> = listOf()





        val imageRecyclerView = binding.profileImageRV

        val adapter = ProfileImageAdapter(imageList) { image ->
            Log.d("ImageSelectorFragment", "Selected image: ${image.imageId}")
        }

        imageRecyclerView.apply {
            this.adapter = adapter
            layoutManager = GridLayoutManager(requireContext(), 4)
        }

        viewModel.imageList.observe(viewLifecycleOwner) {
            Log.d("ImageSelectorFragment", "Fetched images: ${it?.size}")
            adapter.setDataList(viewModel.imageList.value!!)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}